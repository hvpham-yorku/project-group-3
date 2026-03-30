package com.yupathbuilder.backend.authentication.dto;

import jakarta.validation.constraints.NotBlank;


/**
 * Request payload for the login endpoint.
 *
 * @param email user-supplied email address, normalized by the service layer
 * @param password raw password to be checked against the stored hash
 */
public record LoginRequest(
        @NotBlank String email,
        @NotBlank String password
) {}
