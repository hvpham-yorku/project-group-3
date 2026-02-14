package com.yupathbuilder.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * DTO (Data Transfer Object) used by the authentication endpoints.
 */

public record RegisterRequest(
        @NotBlank @Size(min = 3, max = 30) String username,
        @NotBlank @Size(min = 6, max = 100) String password
) {}
