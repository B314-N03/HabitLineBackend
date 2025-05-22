package com.backend.hl.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.backend.hl.model.enums.PriorityEnum;
import com.backend.hl.model.enums.TaskStatusEnum;
import com.backend.hl.model.enums.TaskTypeEnum;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String title;
    private String description;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(name = "last_updated_at", updatable = false)
    private LocalDateTime lastUpdatedAt;
    private Boolean completed;
    private UUID userId;
    private UUID projectId;
    @Enumerated(EnumType.STRING)
    private TaskTypeEnum taskType;
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status;

    public Task() {}

    // Parameterized constructor with custom values
    public Task(String title, String description, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, boolean completed, UUID userId, UUID projectId, TaskTypeEnum taskType, PriorityEnum priority) {
        this.title = title;
        this.description = description;
        this.completed = completed; 
        this.userId = userId;
        this.projectId = projectId;
        this.taskType = taskType;
        this.priority = priority;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public TaskTypeEnum getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskTypeEnum taskType) {
        this.taskType = taskType;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }    

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

}