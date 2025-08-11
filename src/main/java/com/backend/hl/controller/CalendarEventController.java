package com.backend.hl.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hl.model.CalendarEvent;
import com.backend.hl.model.CalendarType;
import com.backend.hl.service.CalendarEventService;

@RestController
@RequestMapping("/api/calendar-events")
public class CalendarEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    @GetMapping("/types")
    public CalendarType[] getCalendarTypes() {
        return calendarEventService.getCalendarTypes(null);
    }

    @GetMapping("/types/create")
    public CalendarType createCalendarType(CalendarType calendarType) {
        return calendarEventService.createCalendarType(calendarType);
    }

    @GetMapping("/types/{id}")
    public CalendarType getCalendarTypeById(@PathVariable("id") UUID id) {
        return calendarEventService.getCalendarTypeById(id);
    }

    @PostMapping("/types/update")
    public CalendarType updateCalendarType(@RequestBody CalendarType calendarType) {
        return calendarEventService.updateCalendarType(calendarType);
    }

    @DeleteMapping("/types/delete/{id}")
    public void deleteCalendarType(@PathVariable("id") UUID id) {
        calendarEventService.deleteCalendarType(id);
    }

    @GetMapping
    public List<CalendarEvent> getAllEvents() {
        return calendarEventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public CalendarEvent getEventById(UUID id) {
        return calendarEventService.getEventById(id);
    }

    @PostMapping("/create")
    public CalendarEvent createEvent(@RequestBody CalendarEvent event) {
        return calendarEventService.create(event);
    }

    @PostMapping("/update")
    public CalendarEvent updateEvent(@RequestBody CalendarEvent event) {
        return calendarEventService.update(event);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable("id") UUID id) {
        calendarEventService.delete(id);
    }

}
