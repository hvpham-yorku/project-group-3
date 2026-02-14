package com.yupathbuilder.backend.auth;

import java.util.Set;


/**
 * Authentication / user domain component: AppUser.
 */

public record AppUser(
        String username,
        String passwordHash,
        Set<String> roles
) {}
