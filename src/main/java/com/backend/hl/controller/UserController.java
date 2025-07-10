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
import com.backend.hl.dto.UserResponseFrontend;
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
            .map(user -> new UserResponseFrontend(user.getId(), user.getUsername(), user.getEmail(), user.getRole()))
            .toList();
    }

    @GetMapping("/{id}")
    public UserResponseFrontend getUserById(@PathVariable("id") UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        return new UserResponseFrontend(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    @PostMapping("/create")
    public UserResponseFrontend createUser(@RequestBody User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(java.time.LocalDateTime.now());
        }
        User savedUser = userRepository.save(user);
        return new UserResponseFrontend(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole());
    }

    @PostMapping("/update")
    public UserResponseFrontend updateUser(@RequestBody User user) {
        UUID userId = user.getId();
        User oldUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if(user.getUsername() != null) oldUser.setUsername(user.getUsername());
        if(user.getEmail() != null) oldUser.setEmail(user.getEmail());
        // Do NOT update password here
        User updatedUser = userRepository.save(oldUser);
        return new UserResponseFrontend(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getRole());
    }
}