package com.yupathbuilder.backend.authentication.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA representation of an authenticated user account stored in the
 * {@code users} table.
 *
 * <p>This entity stores authentication credentials together with the minimal
 * profile data required by the application.</p>
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", length = 60)
    private String firstName;

    @Column(name = "last_name", length = 60)
    private String lastName;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "program_id")
    private Long programId;

    @Lob
    // Stored as a data URL string so the frontend can render it directly.
    @Column(name = "profile_image_data")
    private String profileImageData;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserEntity() {}

    public UserEntity(String email, String passwordHash, Long programId) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.programId = programId;
    }

    public UserEntity(String email, String firstName, String lastName, String passwordHash, Long programId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.programId = programId;
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPasswordHash() { return passwordHash; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Long getProgramId() { return programId; }

    public void setProgramId(Long programId) { this.programId = programId; }

    public String getProfileImageData() { return profileImageData; }

    public void setProfileImageData(String profileImageData) { this.profileImageData = profileImageData; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
