package com.yupathbuilder.backend.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank @Size(min = 6, max = 100) String currentPassword,
        @NotBlank @Size(min = 6, max = 100) String newPassword,
        @NotBlank @Size(min = 6, max = 100) String confirmPassword
) {}
