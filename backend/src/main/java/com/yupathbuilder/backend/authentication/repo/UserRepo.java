package com.yupathbuilder.backend.authentication.repo;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Persistence gateway for authentication user records.
 */
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    /**
     * Resolves a user by normalized email address for login and authenticated
     * profile operations.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks whether an account already exists for the provided email address.
     */
    boolean existsByEmail(String email);
}
