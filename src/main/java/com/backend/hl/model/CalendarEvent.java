package com.backend.hl.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Type;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "calendar_events")
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String title;
    private String description;
    private String start;
    @Column(name = "end_time")
    private String end;
    private String location;

    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private CalendarType calendar;

    @Column(name = "people", columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private String[] people;

    public CalendarEvent() {
    }

    public CalendarEvent(
            UUID id,
            String title,
            String description,
            String start,
            String end,
            String location,
            CalendarType calendar,
            String[] people) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.location = location;
        this.calendar = calendar;
        this.people = people;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CalendarType getCalendar() {
        return calendar;
    }

    public void setCalendar(CalendarType calendar) {
        this.calendar = calendar;
    }

    public String[] getPeople() {
        return people;
    }

    public void setPeople(String[] people) {
        this.people = people;
    }
}
