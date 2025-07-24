package com.backend.hl.service;

import com.backend.hl.model.DailyTask;
import com.backend.hl.repository.DailyTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DailyTaskService {

    private final DailyTaskRepository dailyTaskRepository;

    public DailyTaskService(DailyTaskRepository dailyTaskRepository) {
        this.dailyTaskRepository = dailyTaskRepository;
    }

    public List<DailyTask> getAllTasks() {
        return dailyTaskRepository.findAll();
    }

    public DailyTask getById(UUID id) {
        return dailyTaskRepository.findById(id).orElse(null);
    }

    public DailyTask create(DailyTask task) {
        return dailyTaskRepository.save(task);
    }

    @Transactional
    public DailyTask update(DailyTask updatedTask) {
        UUID id = updatedTask.getId();
        DailyTask existing = dailyTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (updatedTask.getTitle() != null) {
            existing.setTitle(updatedTask.getTitle());
        }

        if (updatedTask.getCompleted()) {
            existing.setCompleted(true);
        }

        return dailyTaskRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!dailyTaskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        dailyTaskRepository.deleteById(id);
    }
}
