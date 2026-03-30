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

/**
 * Exposes the public authentication endpoints and the authenticated profile
 * endpoints for the backend application.
 *
 * <p>This controller is intentionally thin: request validation is handled by
 * Spring, while authentication and profile business rules are delegated to the
 * corresponding services.</p>
 */
@RestController
@RequestMapping("/api/authentication")
public class AuthController {

    private final AuthService authService;
    private final UserProfileService userProfileService;

    public AuthController(AuthService authService, UserProfileService userProfileService) {
        this.authService = authService;
        this.userProfileService = userProfileService;
    }

    /**
     * Registers a new user account and immediately returns an access token for
     * the new session.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest req) {

        return ResponseEntity.ok(authService.register(req));
    }

    /**
     * Authenticates an existing user and returns a JWT for subsequent requests.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest req) {

        return ResponseEntity.ok(authService.login(req));
    }

    /**
     * Lightweight authenticated endpoint used to verify that the security
     * filter chain accepted the caller's token.
     */
    @GetMapping("/me")
    public String me() {
        return "Authenticated";
    }

    /**
     * Returns the profile of the currently authenticated user.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> profile(Authentication auth) {
        return ResponseEntity.ok(userProfileService.getProfile(auth.getName()));
    }

    /**
     * Updates editable profile fields for the currently authenticated user.
     */
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication auth,
            @Valid @RequestBody UpdateProfileRequest req) {

        return ResponseEntity.ok(userProfileService.updateProfile(auth.getName(), req));
    }

    /**
     * Changes the password of the currently authenticated user after the
     * current credential has been verified.
     */
    @PutMapping("/profile/password")
    public ResponseEntity<Void> changePassword(
            Authentication auth,
            @Valid @RequestBody ChangePasswordRequest req) {

        userProfileService.changePassword(auth.getName(), req);
        return ResponseEntity.noContent().build();
    }
}
