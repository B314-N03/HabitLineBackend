package com.backend.hl.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private UUID userId;
    private String title;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;
    private Integer doneTasks;
    private Integer openTasks;
    private String description;
    private String projectColor;

    public Project() {}

    public Project(String title, String description, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, Integer doneTasks, Integer openTasks, String projectColor) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.doneTasks = doneTasks;
        this.openTasks = openTasks;
        this.projectColor = projectColor;
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

    public Integer getDoneTasks() {
        return doneTasks;
    }

    public void setDoneTasks(Integer doneTasks) {
        this.doneTasks = doneTasks;
    }

    public Integer getOpenTasks() {
        return openTasks;
    }

    public void setOpenTasks(Integer openTasks) {
        this.openTasks = openTasks;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getProjectColor() {
        return projectColor;
    }

    public void setProjectColor(String projectColor) {
        this.projectColor = projectColor;
    }
}