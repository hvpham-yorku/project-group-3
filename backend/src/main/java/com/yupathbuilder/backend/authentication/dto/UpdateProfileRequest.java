package com.yupathbuilder.backend.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank @Size(min = 1, max = 60) String firstName,
        @NotBlank @Size(min = 1, max = 60) String lastName,
        @NotNull Long programId,
        String profileImageData,
        boolean removeProfileImage
) {}
