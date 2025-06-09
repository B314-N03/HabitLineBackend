package com.backend.hl.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.backend.hl.repository.DailyTaskRepository;

@RestController
@RequestMapping("/api/daily-tasks")
public class DailyTaskController {
    @Autowired
    private DailyTaskRepository dailyTaskRepository;

    @GetMapping
    public List<DailyTask> getDailyTasks() {
        return dailyTaskRepository.findAll();
    }

    @GetMapping("/{id}")
    public DailyTask getDailyTaskById(@PathVariable("id") String id) {
        return dailyTaskRepository.findById(UUID.fromString(id))
        .orElse(null);
    }


    @PostMapping("/create")
    public DailyTask creaDailyTask(@RequestBody DailyTask dailyTask){
        return dailyTaskRepository.save(dailyTask);
    }

    @PostMapping("/update")
    public DailyTask updateDailyTask(@RequestBody DailyTask dailyTask){
        UUID dailyTaskId = dailyTask.getId();
        DailyTask oldDailyTask = dailyTaskRepository.findById(dailyTaskId)
                .orElseThrow(() -> new RuntimeException("daily-task not found with id: " + dailyTaskId));
        if(dailyTask.getTitle() != null) oldDailyTask.setTitle(dailyTask.getTitle());
        if(dailyTask.getCompleted()) oldDailyTask.setCompleted(dailyTask.getCompleted());
        return dailyTaskRepository.save(oldDailyTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDailyTask(@PathVariable("id") String id){
        UUID uuid = UUID.fromString(id);
        if(!dailyTaskRepository.existsById(uuid)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Daily Task not Found!");
        }
        else{
            dailyTaskRepository.deleteById(uuid);
        }
        return ResponseEntity.ok("Daily Task deleted successfully");
    }
    
}
