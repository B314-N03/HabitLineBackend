package com.backend.hl.service;

import com.backend.hl.dto.AuthenticationRequest;
import com.backend.hl.dto.AuthenticationResponse;
import com.backend.hl.dto.UserResponseFrontend;
import com.backend.hl.model.User;
import com.backend.hl.repository.UserRepository;
import com.backend.hl.security.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authManager, UserRepository userRepository, JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            Map<String, String> error = Map.of(
                    "type", "error",
                    "message", "user with this email already exists");
            return ResponseEntity.badRequest().body(error);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(""); // optional: handle this better
        userRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    public ResponseEntity<Map<String, Object>> login(AuthenticationRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserResponseFrontend userResponse = UserResponseFrontend.createUserResponseFrontend(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userResponse);

        return ResponseEntity.ok(response);
    }

    public UserResponseFrontend getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserResponseFrontend.createUserResponseFrontend(user);
    }

    public AuthenticationResponse refreshToken(String oldToken) {
        if (oldToken == null || oldToken.isEmpty()) {
            return new AuthenticationResponse("Invalid token");
        }
        String refreshed = jwtService.refreshToken(oldToken);
        return new AuthenticationResponse(refreshed);
    }
}
