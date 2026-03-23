package com.yupathbuilder.backend.authentication;

import com.yupathbuilder.backend.authentication.dto.AuthResponse;
import com.yupathbuilder.backend.authentication.dto.ChangePasswordRequest;
import com.yupathbuilder.backend.authentication.dto.LoginRequest;
import com.yupathbuilder.backend.authentication.dto.RegisterRequest;
import com.yupathbuilder.backend.authentication.dto.UpdateProfileRequest;
import com.yupathbuilder.backend.authentication.dto.UserProfileResponse;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthController {

    private final AuthService authService;
    private final UserProfileService userProfileService;

    public AuthController(AuthService authService, UserProfileService userProfileService) {
        this.authService = authService;
        this.userProfileService = userProfileService;
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

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> profile(Authentication auth) {
        return ResponseEntity.ok(userProfileService.getProfile(auth.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication auth,
            @Valid @RequestBody UpdateProfileRequest req) {

        return ResponseEntity.ok(userProfileService.updateProfile(auth.getName(), req));
    }

    @PutMapping("/profile/password")
    public ResponseEntity<Void> changePassword(
            Authentication auth,
            @Valid @RequestBody ChangePasswordRequest req) {

        userProfileService.changePassword(auth.getName(), req);
        return ResponseEntity.noContent().build();
    }
}
