package com.backend.hl.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.hl.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

}
