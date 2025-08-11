package com.backend.hl.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ColorThemeConverter implements AttributeConverter<ColorTheme, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ColorTheme colorTheme) {
        try {
            return objectMapper.writeValueAsString(colorTheme);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting ColorTheme to JSON", e);
        }
    }

    @Override
    public ColorTheme convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ColorTheme.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to ColorTheme", e);
        }
    }
}
