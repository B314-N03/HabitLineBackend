package com.backend.hl.controller;

import com.backend.hl.repository.TaskRepository;
import com.backend.hl.model.Task;
import com.backend.hl.model.enums.TaskStatusEnum;

import java.util.List;
import java.util.UUID;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        task.setCreatedAt(java.time.LocalDateTime.now());
        task.setLastUpdatedAt(java.time.LocalDateTime.now());
        task.setCompleted(false);
        return taskRepository.save(task);
    }
    @PostMapping("/update")
    public Task updateTask(@RequestBody Task task) {
        UUID taskId = task.getId();
        Task oldTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        
        // Update only the fields that are not null meaning the user wants to update them
        if (task.getTitle() != null) oldTask.setTitle(task.getTitle());
        if (task.getDescription() != null) oldTask.setDescription(task.getDescription());
        if (task.getUserId() != null) oldTask.setUserId(task.getUserId());
        if (task.getProjectId() != null) oldTask.setProjectId(task.getProjectId());
        if (task.getTaskType() != null) oldTask.setTaskType(task.getTaskType());
        if (task.getPriority() != null) oldTask.setPriority(task.getPriority());
        if (task.getCompleted() != null) oldTask.setCompleted(task.getCompleted());
        if (task.getStatus() != null){
            oldTask.setStatus(task.getStatus());
            oldTask.setCompleted(task.getStatus() == TaskStatusEnum.done);
        };
        oldTask.setLastUpdatedAt(java.time.LocalDateTime.now());
        
        return taskRepository.save(oldTask);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, String> deleteTask(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (!taskRepository.existsById(uuid)) {
            return Map.of("message", "Task not found");
        }
        taskRepository.deleteById(uuid);
        return Map.of("message", "Task deleted successfully");
    }

}
