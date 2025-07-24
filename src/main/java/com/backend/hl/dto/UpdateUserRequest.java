package com.backend.hl.dto;

import java.util.UUID;

import com.backend.hl.model.enums.ThemeEnum;

public class UpdateUserRequest {
    private UUID id;
    private String username;
    private String email;
    private ThemeEnum theme;
    private WeatherDTO weather;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(UUID id, String username, String email, String role,
            ThemeEnum theme, WeatherDTO weather) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.theme = theme;
        this.weather = weather;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public ThemeEnum getTheme() {
        return theme;
    }

    public WeatherDTO getWeather() {
        return weather;
    }
}
