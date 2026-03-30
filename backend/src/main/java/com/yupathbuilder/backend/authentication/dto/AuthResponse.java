package com.yupathbuilder.backend.authentication.dto;


/**
 * Response payload returned after successful authentication operations.
 *
 * @param token signed JWT used for subsequent authenticated requests
 * @param username identifier echoed back to the client for session state
 */
public record AuthResponse(
        String token,
        String username
) {}
