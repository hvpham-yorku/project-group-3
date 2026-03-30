package com.yupathbuilder.backend.authentication.dto;

/**
 * Sanitized profile payload returned to authenticated clients.
 *
 * @param id persistent user identifier
 * @param email normalized account email
 * @param firstName current first name
 * @param lastName current last name
 * @param programId selected program identifier
 * @param facultyId faculty identifier derived from the selected program
 * @param programName display name of the selected program
 * @param programDegree degree label associated with the selected program
 * @param profileImageData stored profile image data URL, when present
 */
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
