package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.auth.UserService;
import com.yupathbuilder.backend.auth.dto.AuthResponse;
import com.yupathbuilder.backend.auth.dto.LoginRequest;
import com.yupathbuilder.backend.auth.dto.RegisterRequest;
import com.yupathbuilder.backend.auth.jwt.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



/**
 * REST controller for the AuthController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthController(UserService users, PasswordEncoder encoder, JwtUtil jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            var user = users.register(req.username(), req.password());
            String token = jwt.generateToken(user.username(), user.roles().stream().toList());
            return ResponseEntity.ok(new AuthResponse(token, user.username()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        var userOpt = users.find(req.username());
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("Invalid credentials");

        var user = userOpt.get();
        if (!encoder.matches(req.password(), user.passwordHash())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwt.generateToken(user.username(), user.roles().stream().toList());
        return ResponseEntity.ok(new AuthResponse(token, user.username()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        // Frontend usually already knows the username from token response;
        // this endpoint exists as a sanity check.
        return ResponseEntity.ok("OK");
    }
}
