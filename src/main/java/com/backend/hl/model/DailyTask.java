package com.backend.hl.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "daily_tasks")
public class DailyTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private boolean completed;
    private String title;


    public DailyTask() {
    }

    public DailyTask(boolean completed, String title) {
        this.completed = completed;
        this.title = title;
    }


    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public boolean getCompleted(){
        return completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
