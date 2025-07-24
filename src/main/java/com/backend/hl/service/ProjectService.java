package com.backend.hl.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.backend.hl.model.Project;
import com.backend.hl.model.Task;
import com.backend.hl.repository.ProjectRepository;
import com.backend.hl.repository.TaskRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            project.setOpenTasks(projectRepository.getOpenTasksByProjectId(project.getId()));
            project.setDoneTasks(projectRepository.getDoneTasksByProjectId(project.getId()));
        }
        return projects;
    }

    public Project getById(UUID id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project create(Project project) {
        project.setCreatedAt(java.time.LocalDateTime.now());
        project.setLastUpdatedAt(java.time.LocalDateTime.now());
        return projectRepository.save(project);
    }

    public Project update(Project updatedProject) {
        UUID id = updatedProject.getId();
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (updatedProject.getTitle() != null)
            existing.setTitle(updatedProject.getTitle());
        if (updatedProject.getDescription() != null)
            existing.setDescription(updatedProject.getDescription());
        if (updatedProject.getProjectColor() != null)
            existing.setProjectColor(updatedProject.getProjectColor());
        if (updatedProject.getUserId() != null)
            existing.setUserId(updatedProject.getUserId());

        existing.setLastUpdatedAt(java.time.LocalDateTime.now());

        return projectRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }

        List<Task> tasks = projectRepository.getTasksByProjectId(id);
        for (Task task : tasks) {
            taskRepository.deleteById(task.getId());
        }

        projectRepository.deleteById(id);
    }
}
