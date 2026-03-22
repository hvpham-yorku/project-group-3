package com.yupathbuilder.backend.authentication;

import com.yupathbuilder.backend.authentication.dto.ChangePasswordRequest;
import com.yupathbuilder.backend.authentication.dto.UpdateProfileRequest;
import com.yupathbuilder.backend.authentication.dto.UserProfileResponse;
import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.entity.ProgramEntity;
import com.yupathbuilder.backend.repo.ProgramRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserProfileResponse getProfile(String email) {
        UserEntity user = getUserByEmail(email);
        return toResponse(user, getProgram(user.getProgramId()));
    }

    public UserProfileResponse updateProfile(String email, UpdateProfileRequest req) {
        UserEntity user = getUserByEmail(email);
        ProgramEntity program = getRequiredProgram(req.programId());

        user.setFirstName(req.firstName().trim());
        user.setLastName(req.lastName().trim());
        user.setProgramId(program.getId());

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

    private UserEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private ProgramEntity getProgram(Long programId) {
        if (programId == null) {
            return null;
        }
        return programRepo.findWithFacultyById(programId).orElse(null);
    }

    private ProgramEntity getRequiredProgram(Long programId) {
        return programRepo.findWithFacultyById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Selected program does not exist"));
    }

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
