package com.yupathbuilder.backend.authentication;

import com.yupathbuilder.backend.authentication.dto.*;
import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.jwt.JwtUtil;
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

    String email = req.email().trim().toLowerCase();

    if (repo.existsByEmail(email))
        throw new IllegalArgumentException("User already exists");

    UserEntity user = new UserEntity(
            email,
            req.firstName().trim(),
            req.lastName().trim(),
            encoder.encode(req.password()),
            req.programId()
    );

    repo.save(user);

    String token = jwt.generateToken(email, "USER");

    return new AuthResponse(token, email);
    }

    /*
     * Login user
     */
    public AuthResponse login(LoginRequest req) {

    String email = req.email().trim().toLowerCase();

    UserEntity user = repo.findByEmail(email)
            .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

    if (!encoder.matches(req.password(), user.getPasswordHash()))
        throw new InvalidCredentialsException("Invalid credentials");

    String token = jwt.generateToken(user.getEmail(), "USER");

    return new AuthResponse(token, user.getEmail());
}
}
