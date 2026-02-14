package com.yupathbuilder.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;


/**
 * DTO (Data Transfer Object) used by the authentication endpoints.
 */

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {}
