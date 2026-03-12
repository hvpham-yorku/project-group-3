package com.yupathbuilder.backend.authentication.dto;


/**
 * DTO (Data Transfer Object) used by the authentication endpoints.
 */

public record AuthResponse(
        String token,
        String username
) {}
