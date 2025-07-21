package com.backend.hl.dto;

import java.util.UUID;

import com.backend.hl.model.User;
import com.backend.hl.model.Weather;
import com.backend.hl.model.enums.ThemeEnum;

public class UserResponseFrontend {
    private UUID id;
    private String username;
    private String email;
    private String role;
    private ThemeEnum theme;
    private WeatherDTO weather;

    public UserResponseFrontend(UUID id, String username, String email, String role,
            ThemeEnum theme, WeatherDTO weather) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.theme = theme;
        this.weather = weather;
    }

    public static UserResponseFrontend createUserResponseFrontend(User user) {
        Weather weather = user.getWeather();
        WeatherDTO weatherDTO = weather != null
                ? new WeatherDTO(weather.getRegion(), weather.getLat(), weather.getLon())
                : null;

        return new UserResponseFrontend(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getTheme(),
                weatherDTO);
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

    public String getRole() {
        return role;
    }

    public ThemeEnum getTheme() {
        return theme;
    }

    public WeatherDTO getWeather() {
        return weather;
    }
}
