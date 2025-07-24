package com.backend.hl.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.backend.hl.dto.UpdateUserRequest;
import com.backend.hl.dto.WeatherDTO;
import com.backend.hl.model.User;
import com.backend.hl.model.Weather;
import com.backend.hl.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User createUser(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(java.time.LocalDateTime.now());
        }
        return userRepository.save(user);
    }

    public User updateUser(UpdateUserRequest request) {
        UUID userId = request.getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (request.getUsername() != null)
            user.setUsername(request.getUsername());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getTheme() != null)
            user.setTheme(request.getTheme());

        if (request.getWeather() != null) {
            WeatherDTO w = request.getWeather();
            Weather weather = user.getWeather();
            if (weather == null) {
                weather = new Weather();
            }
            if (w.getLat() != null)
                weather.setLat(w.getLat());
            if (w.getLon() != null)
                weather.setLon(w.getLon());
            if (w.getRegion() != null)
                weather.setRegion(w.getRegion());
            user.setWeather(weather);
        }

        return userRepository.save(user);
    }
}
