package com.backend.hl.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.hl.model.CalendarType;

public interface CalendarTypeRepository extends JpaRepository<CalendarType, UUID> {
    @Query("SELECT ct FROM CalendarType ct WHERE ct.userId = :userId")
    List<CalendarType> findByUserId(@Param("userId") UUID userId);
}
