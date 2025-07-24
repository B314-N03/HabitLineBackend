package com.backend.hl.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hl.model.DailyTask;
import com.backend.hl.service.DailyTaskService;

@RestController
@RequestMapping("/api/daily-tasks")
public class DailyTaskController {

    private final DailyTaskService dailyTaskService;

    public DailyTaskController(DailyTaskService dailyTaskService) {
        this.dailyTaskService = dailyTaskService;
    }

    @GetMapping
    public List<DailyTask> getDailyTasks() {
        return dailyTaskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public DailyTask getDailyTaskById(@PathVariable String id) {
        return dailyTaskService.getById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public DailyTask createDailyTask(@RequestBody DailyTask task) {
        return dailyTaskService.create(task);
    }

    @PostMapping("/update")
    public DailyTask updateDailyTask(@RequestBody DailyTask task) {
        return dailyTaskService.update(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDailyTask(@PathVariable String id) {
        try {
            dailyTaskService.delete(UUID.fromString(id));
            return ResponseEntity.ok("Deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
