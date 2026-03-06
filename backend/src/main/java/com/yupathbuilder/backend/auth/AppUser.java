package com.yupathbuilder.backend.auth;

import java.util.Set;

public record AppUser(
        String username,        // aquí guardaremos el email (login)
        String passwordHash,
        Set<String> roles,
        String firstName,
        String lastName,
        Long programId
) {}