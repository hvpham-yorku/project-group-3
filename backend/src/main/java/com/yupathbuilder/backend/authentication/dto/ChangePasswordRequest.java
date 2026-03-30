package com.yupathbuilder.backend.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request payload for authenticated password updates.
 *
 * @param currentPassword current raw password used to prove account ownership
 * @param newPassword replacement raw password requested by the user
 * @param confirmPassword confirmation copy used to validate the new password
 */
public record ChangePasswordRequest(
        @NotBlank @Size(min = 6, max = 100) String currentPassword,
        @NotBlank @Size(min = 6, max = 100) String newPassword,
        @NotBlank @Size(min = 6, max = 100) String confirmPassword
) {}
