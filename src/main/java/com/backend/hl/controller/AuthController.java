package com.backend.hl.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hl.dto.AuthenticationRequest;
import com.backend.hl.dto.AuthenticationResponse;
import com.backend.hl.dto.RefreshTokenRequest;
import com.backend.hl.dto.UserResponseFrontend;
import com.backend.hl.model.User;
import com.backend.hl.repository.UserRepository;
import com.backend.hl.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            Map<String, String> errorResponse = Map.of(
                "type", "error",
                "message", "user with this email already exists"
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(""); // Prevent NPE
        userRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequest request) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());

        // Fetch user details from DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserResponseFrontend userResponse = new UserResponseFrontend(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseFrontend> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserResponseFrontend userResponse = new UserResponseFrontend(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String token = refreshTokenRequest.getToken();
        if( token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse("Invalid token"));
        }
        String refreshedToken = jwtService.refreshToken(token);
        return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
    }

}
