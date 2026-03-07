package com.yupathbuilder.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(min = 1, max = 60) String firstName,
        @NotBlank @Size(min = 1, max = 60) String lastName,

        @NotBlank @Email @Size(max = 120) String email,

        @NotNull Long programId,

        @NotBlank @Size(min = 6, max = 100) String password,
        @NotBlank @Size(min = 6, max = 100) String confirmPassword
) {}