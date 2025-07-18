package com.backend.hl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.backend.hl.repository.UserRepository;
import com.backend.hl.model.User;
import com.backend.hl.model.Weather;
import com.backend.hl.dto.UserResponseFrontend;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserResponseFrontend> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponseFrontend.createUserResponseFrontend(user))
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseFrontend getUserById(@PathVariable("id") UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return null;
        return UserResponseFrontend.createUserResponseFrontend(user);
    }

    @PostMapping("/create")
    public UserResponseFrontend createUser(@RequestBody User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(java.time.LocalDateTime.now());
        }
        User savedUser = userRepository.save(user);
        return UserResponseFrontend.createUserResponseFrontend(savedUser);
    }

    @PostMapping("/update")
    public UserResponseFrontend updateUser(@RequestBody User user) {
        UUID userId = user.getId();
        User oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Arrays.stream(User.class.getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Object newValue = field.get(user);

                        if (newValue != null) {
                            // Handle Weather object separately
                            if (field.getName().equals("weather") && newValue instanceof Weather newWeather) {
                                Weather oldWeather = oldUser.getWeather();
                                if (oldWeather == null) {
                                    oldUser.setWeather(newWeather);
                                } else {
                                    Arrays.stream(Weather.class.getDeclaredFields())
                                            .forEach(weatherField -> {
                                                weatherField.setAccessible(true);
                                                try {
                                                    Object weatherValue = weatherField.get(newWeather);
                                                    if (weatherValue != null) {
                                                        weatherField.set(oldWeather, weatherValue);
                                                    }
                                                } catch (IllegalAccessException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            });
                                }
                            } else {
                                field.set(oldUser, newValue);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        User updatedUser = userRepository.save(oldUser);
        return UserResponseFrontend.createUserResponseFrontend(updatedUser);
    }

}