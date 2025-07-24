package com.backend.hl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.hl.dto.UpdateUserRequest;
import com.backend.hl.dto.UserResponseFrontend;
import com.backend.hl.model.User;
import com.backend.hl.service.UserService;
import com.backend.hl.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserResponseFrontend> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseFrontend::createUserResponseFrontend)
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
        User savedUser = userService.createUser(user);
        return UserResponseFrontend.createUserResponseFrontend(savedUser);
    }

    @PostMapping("/update")
    public UserResponseFrontend updateUser(@RequestBody UpdateUserRequest request) {
        User updatedUser = userService.updateUser(request);
        return UserResponseFrontend.createUserResponseFrontend(updatedUser);
    }
}
