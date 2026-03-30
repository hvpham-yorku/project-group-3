package com.yupathbuilder.backend.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request payload for new account registration.
 *
 * @param firstName user first name captured during onboarding
 * @param lastName user last name captured during onboarding
 * @param email user email address used as the unique login identifier
 * @param programId selected academic program linked to the account
 * @param password raw password to hash before persistence
 * @param confirmPassword confirmation copy used to prevent accidental mismatch
 */
public record RegisterRequest(
        @NotBlank @Size(min = 1, max = 60) String firstName,
        @NotBlank @Size(min = 1, max = 60) String lastName,

        @NotBlank @Email @Size(max = 120) String email,

        @NotNull Long programId,

        @NotBlank @Size(min = 6, max = 100) String password,
        @NotBlank @Size(min = 6, max = 100) String confirmPassword
) {}
