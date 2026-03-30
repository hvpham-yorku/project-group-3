package com.yupathbuilder.backend.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request payload for editing the authenticated user's profile details.
 *
 * @param firstName updated first name
 * @param lastName updated last name
 * @param programId updated academic program selection
 * @param profileImageData optional image data URL to store as the profile photo
 * @param removeProfileImage explicit flag indicating that any stored image
 *                           should be removed
 */
public record UpdateProfileRequest(
        @NotBlank @Size(min = 1, max = 60) String firstName,
        @NotBlank @Size(min = 1, max = 60) String lastName,
        @NotNull Long programId,
        String profileImageData,
        boolean removeProfileImage
) {}
