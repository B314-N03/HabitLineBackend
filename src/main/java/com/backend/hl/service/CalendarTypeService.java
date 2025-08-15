package com.backend.hl.service;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.backend.hl.model.CalendarType;
import com.backend.hl.repository.CalendarTypeRepository;

@Service
@Transactional
public class CalendarTypeService {

    private final CalendarTypeRepository calendarTypeRepository;

    public CalendarTypeService(CalendarTypeRepository calendarTypeRepository) {
        this.calendarTypeRepository = calendarTypeRepository;
    }

    public CalendarType[] getCalendarTypes(UUID userId) {
        List<CalendarType> types = calendarTypeRepository.findByUserId(userId);
        return types.toArray(new CalendarType[0]);
    }

    public CalendarType getCalendarTypeById(UUID id) {
        return calendarTypeRepository.findById(id).orElse(null);
    }

    public CalendarType createCalendarType(CalendarType calendarType) {
        return calendarTypeRepository.save(calendarType);
    }

    public CalendarType updateCalendarType(CalendarType calendarType) {
        UUID id = calendarType.getId();
        CalendarType existingType = calendarTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendar Type not found"));

        if (calendarType.getName() != null) {
            existingType.setName(calendarType.getName());
        }
        if (calendarType.getColorTheme() != null) {
            existingType.setColorTheme(calendarType.getColorTheme());
        }

        return calendarTypeRepository.save(existingType);
    }

    public void deleteCalendarType(UUID id) {
        calendarTypeRepository.deleteById(id);
    }
}
