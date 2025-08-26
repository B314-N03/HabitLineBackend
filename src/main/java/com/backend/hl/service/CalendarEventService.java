package com.backend.hl.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.backend.hl.model.CalendarEvent;
import com.backend.hl.model.CalendarType;
import com.backend.hl.repository.CalendarEventRepository;
import com.backend.hl.repository.CalendarTypeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final CalendarTypeRepository calendarTypeRepository;

    public CalendarEventService(CalendarEventRepository calendarEventRepository,
            CalendarTypeRepository calendarTypeRepository) {
        this.calendarEventRepository = calendarEventRepository;
        this.calendarTypeRepository = calendarTypeRepository;
    }

    public List<CalendarEvent> getAllEvents() {
        return calendarEventRepository.findAll();
    }

    public CalendarEvent getEventById(UUID id) {
        return calendarEventRepository.findById(id).orElse(null);
    }

    public CalendarEvent create(CalendarEvent event) {
        return calendarEventRepository.save(event);
    }

    public CalendarEvent update(CalendarEvent event) {
        UUID id = event.getId();
        CalendarEvent existingEvent = calendarEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendar Event not found"));

        // Update fields as necessary
        if (event.getTitle() != null) {
            existingEvent.setTitle(event.getTitle());
        }
        if (event.getDescription() != null) {
            existingEvent.setDescription(event.getDescription());
        }
        if (event.getStart() != null) {
            existingEvent.setStart(event.getStart());
        }
        if (event.getEnd() != null) {
            existingEvent.setEnd(event.getEnd());
        }
        if (event.getLocation() != null) {
            existingEvent.setLocation(event.getLocation());
        }
        if (event.getCalendar() != null) {
            UUID calendarId = event.getCalendar().getId();
            CalendarType calendar = calendarTypeRepository.findById(calendarId)
                .orElseThrow(() -> new RuntimeException("Calendar not found"));
            existingEvent.setCalendar(calendar);
        }
        if (event.getPeople() != null) {
            existingEvent.setPeople(event.getPeople());
        }

        return calendarEventRepository.save(existingEvent);
    }



    public CalendarEvent delete(UUID id) {
        CalendarEvent event = calendarEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendar Event not found"));
        calendarEventRepository.delete(event);
        return event;
    }
}
