package com.yupathbuilder.backend.authentication;

import com.yupathbuilder.backend.authentication.dto.*;
import com.yupathbuilder.backend.authentication.jwt.JwtUtil;
import com.yupathbuilder.backend.authentication.model.User;
import com.yupathbuilder.backend.authentication.repo.UserRepo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthService(UserRepo repo, PasswordEncoder encoder, JwtUtil jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    /*
     * Register new user
     */
    public AuthResponse register(RegisterRequest req) {

        if (!req.password().equals(req.confirmPassword()))
            throw new IllegalArgumentException("Passwords do not match");

        String email = req.email().toLowerCase();

        if (repo.exists(email))
            throw new IllegalArgumentException("User already exists");

        User user = new User(
                email,
                encoder.encode(req.password()),
                req.firstName(),
                req.lastName(),
                req.programId(),
                UserRole.STUDENT
        );

        repo.save(user);

        String token = jwt.generateToken(
                user.getUsername(),
                user.getType().name()
        );

        return new AuthResponse(token, user.getUsername());
    }

    /*
     * Login user
     */
    public AuthResponse login(LoginRequest req) {

        User user = repo.find(req.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPasswordHash()))
            throw new IllegalArgumentException("Invalid credentials");

        String token = jwt.generateToken(
                user.getUsername(),
                user.getType().name()
        );

        return new AuthResponse(token, user.getUsername());
    }
}