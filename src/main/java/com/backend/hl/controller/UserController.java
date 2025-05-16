package com.backend.hl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.hl.repository.UserRepository;
import com.backend.hl.model.User;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@RequestBody String id) {
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        if (user.getCreatedAt() == null) {
        user.setCreatedAt(java.time.LocalDateTime.now());
    }
        return userRepository.save(user);
    }

    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        UUID userId = user.getId();
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if(user.getUsername() != null) oldUser.setUsername(user.getUsername());
        if(user.getEmail() != null) oldUser.setEmail(user.getEmail());
        if(user.getPassword() != null) oldUser.setPassword(user.getPassword());
        return userRepository.save(user);
    }
}