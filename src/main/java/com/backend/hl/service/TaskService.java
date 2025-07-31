package com.backend.hl.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hl.model.Comment;
import com.backend.hl.model.Task;
import com.backend.hl.model.enums.TaskStatusEnum;
import com.backend.hl.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAllWithComments();
    }

    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setLastUpdatedAt(LocalDateTime.now());
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        UUID taskId = task.getId();
        Task oldTask = taskRepository.findByIdWithComments(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        // Update only non-null fields
        if (task.getTitle() != null)
            oldTask.setTitle(task.getTitle());
        if (task.getDescription() != null)
            oldTask.setDescription(task.getDescription());
        if (task.getUserId() != null)
            oldTask.setUserId(task.getUserId());
        if (task.getProjectId() != null)
            oldTask.setProjectId(task.getProjectId());
        if (task.getTaskType() != null)
            oldTask.setTaskType(task.getTaskType());
        if (task.getPriority() != null)
            oldTask.setPriority(task.getPriority());
        if (task.getCompleted() != null)
            oldTask.setCompleted(task.getCompleted());

        if (task.getStatus() != null) {
            oldTask.setStatus(task.getStatus());
            oldTask.setCompleted(task.getStatus() == TaskStatusEnum.done);
        }

        oldTask.setLastUpdatedAt(LocalDateTime.now());

        if (task.getComments() != null) {
            List<Comment> updatedComments = task.getComments().stream()
                    .peek(c -> c.setTask(oldTask))
                    .collect(Collectors.toList());

            oldTask.getComments().clear();
            oldTask.getComments().addAll(updatedComments);
        }

        return taskRepository.save(oldTask);
    }

    public Map<String, String> deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            return Map.of("message", "Task not found");
        }
        taskRepository.deleteById(id);
        return Map.of("message", "Task deleted successfully");
    }
}
