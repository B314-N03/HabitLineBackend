package com.backend.hl.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.hl.model.Comment;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, UUID> {

    @Query("SELECT c FROM Comment c WHERE c.taskId = :taskId")
    List<Comment> getCommentsByTaskId(@Param("taskId") UUID taskId);
}
