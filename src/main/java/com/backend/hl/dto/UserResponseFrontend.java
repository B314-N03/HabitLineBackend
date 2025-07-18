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
    private String region;
    private String lat;
    private String lon;

    public UserResponseFrontend(UUID id, String username, String email, String role, String region, String lat,
            String lon, ThemeEnum theme) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.region = region;
        this.lat = lat;
        this.lon = lon;
        this.theme = theme;
    }

    public static UserResponseFrontend createUserResponseFrontend(User user) {
        Weather weather = user.getWeather();
        return new UserResponseFrontend(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                weather != null ? weather.getRegion() : null,
                weather != null ? weather.getLat() : null,
                weather != null ? weather.getLon() : null,
                user.getTheme());
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

    public String getRegion() {
        return region;
    }

    public ThemeEnum getTheme() {
        return theme;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
