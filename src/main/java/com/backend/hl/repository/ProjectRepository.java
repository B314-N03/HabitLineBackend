package com.backend.hl.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.hl.model.Project;
import com.backend.hl.model.Task;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT COUNT(t) FROM Task t WHERE t.completed = false AND t.projectId = :projectId")
    int getOpenTasksByProjectId(@Param("projectId") UUID projectId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.completed = true AND t.projectId = :projectId")
    int getDoneTasksByProjectId(@Param("projectId") UUID projectId);

    @Query("SELECT t FROM Task t WHERE t.projectId = :projectId")
    List<Task> getTasksByProjectId(@Param("projectId") UUID projectId);
}
