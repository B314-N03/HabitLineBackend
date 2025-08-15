package com.backend.hl.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.backend.hl.model.CalendarType;
import com.backend.hl.service.CalendarTypeService;

@RestController
@RequestMapping("/api/calendar-types")
public class CalendarTypeController {

    private final CalendarTypeService calendarTypeService;

    public CalendarTypeController(CalendarTypeService calendarTypeService) {
        this.calendarTypeService = calendarTypeService;
    }

    @GetMapping
    public CalendarType[] getCalendarTypes() {
        return calendarTypeService.getCalendarTypes(null);
    }

    @PostMapping("/create")
    public CalendarType createCalendarType(@RequestBody CalendarType calendarType) {
        return calendarTypeService.createCalendarType(calendarType);
    }

    @GetMapping("/{id}")
    public CalendarType getCalendarTypeById(@PathVariable("id") UUID id) {
        return calendarTypeService.getCalendarTypeById(id);
    }

    @PostMapping("/update")
    public CalendarType updateCalendarType(@RequestBody CalendarType calendarType) {
        return calendarTypeService.updateCalendarType(calendarType);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCalendarType(@PathVariable("id") UUID id) {
        calendarTypeService.deleteCalendarType(id);
    }
}
