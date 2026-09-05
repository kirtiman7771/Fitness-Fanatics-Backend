package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.LoginRequest;
import com.gym.gym_ff_backend.dto.RegisterRequest;
import com.gym.gym_ff_backend.dto.AuthResponse;
import com.gym.gym_ff_backend.service.AuthService;
import com.gym.gym_ff_backend.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        User user = authService.getUserByEmail(request.getEmail());

        AuthResponse response = new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser() {

        User user = authService.getCurrentUser();

        AuthResponse response = new AuthResponse(
                null,
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(response);
    }
}