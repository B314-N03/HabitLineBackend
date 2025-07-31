package com.backend.hl.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.backend.hl.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("SELECT DISTINCT t FROM Task t LEFT JOIN FETCH t.comments")
    List<Task> findAllWithComments();

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.comments WHERE t.id = :id")
    Optional<Task> findByIdWithComments(UUID id);

}
