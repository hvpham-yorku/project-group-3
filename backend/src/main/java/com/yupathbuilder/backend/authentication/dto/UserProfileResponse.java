package com.yupathbuilder.backend.authentication.dto;

public record UserProfileResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        Long programId,
        Long facultyId,
        String programName,
        String programDegree,
        String profileImageData
) {}
