package com.backend.hl.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

import com.backend.hl.repository.TaskRepository;
import com.backend.hl.repository.ProjectRepository;
import com.backend.hl.model.Project;
import com.backend.hl.model.Task;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @GetMapping
    public List<Project> getProjects() {
        List<Project> projects = projectRepository.findAll();
        for(Project project : projects) {
            project.setOpenTasks(projectRepository.getOpenTasksByProjectId(project.getId())); 
            project.setDoneTasks(projectRepository.getDoneTasksByProjectId(project.getId())); 
        }
        return projects;
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable("id") String id) {
        return projectRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {

        // Set Default Values
        project.setCreatedAt(java.time.LocalDateTime.now());
        project.setLastUpdatedAt(java.time.LocalDateTime.now());

        return projectRepository.save(project);
    }

    @PostMapping("/update")
    public Project updateProject(@RequestBody Project project) {
        UUID projectId = project.getId();
        Project oldProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        if(project.getTitle() != null) oldProject.setTitle(project.getTitle());
        if(project.getDescription() != null) oldProject.setDescription(project.getDescription());
        if(project.getProjectColor() != null) oldProject.setProjectColor(project.getProjectColor());
        if(project.getUserId() != null) oldProject.setUserId(project.getUserId());        
        
        // Update the lastUpdatedAt field
        project.setLastUpdatedAt(java.time.LocalDateTime.now());

        return projectRepository.save(project);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (!projectRepository.existsById(uuid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
        for(Task task : projectRepository.getTasksByProjectId(uuid)) {
            taskRepository.deleteById(task.getId());
        }
        projectRepository.deleteById(uuid);
        return ResponseEntity.ok("Project deleted successfully");
    }
}
