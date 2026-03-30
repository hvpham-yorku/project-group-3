package com.yupathbuilder.backend.authentication;

import com.yupathbuilder.backend.authentication.dto.ChangePasswordRequest;
import com.yupathbuilder.backend.authentication.dto.UpdateProfileRequest;
import com.yupathbuilder.backend.authentication.dto.UserProfileResponse;
import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.repo.ProgramRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handles authenticated user profile reads and updates.
 *
 * <p>This service owns profile-specific validation such as program existence,
 * password change rules, and profile image constraints.</p>
 */
@Service
public class UserProfileService {

    private static final int MAX_PROFILE_IMAGE_LENGTH = 3_000_000;

    private final UserRepo userRepo;
    private final ProgramRepo programRepo;
    private final PasswordEncoder passwordEncoder;

    public UserProfileService(UserRepo userRepo, ProgramRepo programRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.programRepo = programRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Loads the current user's profile and enriches it with related program and
     * faculty details when available.
     */
    public UserProfileResponse getProfile(String email) {
        UserEntity user = getUserByEmail(email);
        return toResponse(user, getProgram(user.getProgramId()));
    }

    /**
     * Updates mutable profile fields for the current user and returns the saved
     * representation.
     */
    public UserProfileResponse updateProfile(String email, UpdateProfileRequest req) {
        UserEntity user = getUserByEmail(email);
        ProgramEntity program = getRequiredProgram(req.programId());

        user.setFirstName(req.firstName().trim());
        user.setLastName(req.lastName().trim());
        user.setProgramId(program.getId());

        // Explicit removal wins over any image payload included in the request.
        if (req.removeProfileImage()) {
            user.setProfileImageData(null);
        } else if (req.profileImageData() != null) {
            String imageData = req.profileImageData().trim();
            validateProfileImage(imageData);
            user.setProfileImageData(imageData.isBlank() ? null : imageData);
        }

        userRepo.save(user);
        return toResponse(user, program);
    }

    /**
     * Changes the current user's password after verifying the existing password
     * and enforcing basic safety rules.
     */
    public void changePassword(String email, ChangePasswordRequest req) {
        UserEntity user = getUserByEmail(email);

        if (!passwordEncoder.matches(req.currentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!req.newPassword().equals(req.confirmPassword())) {
            throw new IllegalArgumentException("New passwords do not match");
        }

        if (passwordEncoder.matches(req.newPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("New password must be different from the current password");
        }

        user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
        userRepo.save(user);
    }

    /**
     * Resolves a user by email and fails fast when the authenticated principal
     * no longer maps to a stored account.
     */
    private UserEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * Loads program details when the user has a selected program. A missing
     * program is tolerated when building a read response.
     */
    private ProgramEntity getProgram(Long programId) {
        if (programId == null) {
            return null;
        }
        return programRepo.findWithFacultyById(programId).orElse(null);
    }

    /**
     * Loads the selected program for profile updates and treats missing program
     * references as invalid client input.
     */
    private ProgramEntity getRequiredProgram(Long programId) {
        return programRepo.findWithFacultyById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Selected program does not exist"));
    }

    /**
     * Performs lightweight validation for profile image data before it is
     * stored as a data URL string.
     */
    private void validateProfileImage(String imageData) {
        if (imageData.isBlank()) {
            return;
        }
        if (!imageData.startsWith("data:image/")) {
            throw new IllegalArgumentException("Profile photo must be an image");
        }
        if (imageData.length() > MAX_PROFILE_IMAGE_LENGTH) {
            throw new IllegalArgumentException("Profile photo is too large");
        }
    }

    /**
     * Builds the API response while flattening related program and faculty
     * information for frontend consumption.
     */
    private UserProfileResponse toResponse(UserEntity user, ProgramEntity program) {
        Long facultyId = null;
        String programName = null;
        String programDegree = null;

        if (program != null) {
            programName = program.getName();
            programDegree = program.getDegree();
            if (program.getFaculty() != null) {
                facultyId = program.getFaculty().getId();
            }
        }

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProgramId(),
                facultyId,
                programName,
                programDegree,
                user.getProfileImageData()
        );
    }
}

