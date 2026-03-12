package com.yupathbuilder.backend.authentication.dto;

import jakarta.validation.constraints.NotBlank;


/**
 * DTO (Data Transfer Object) used by the authentication endpoints.
 */

public record LoginRequest(
        @NotBlank String email,
        @NotBlank String password
) {}
