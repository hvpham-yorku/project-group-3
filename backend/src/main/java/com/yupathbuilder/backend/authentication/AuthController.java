package com.yupathbuilder.backend.authentication;

import com.yupathbuilder.backend.authentication.dto.AuthResponse;
import com.yupathbuilder.backend.authentication.dto.LoginRequest;
import com.yupathbuilder.backend.authentication.dto.RegisterRequest;

import jakarta.validation.Valid;

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
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest req) {

        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest req) {

        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/me")
    public String me() {
        return "Authenticated";
    }
}