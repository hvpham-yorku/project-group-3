package com.yupathbuilder.backend.authentication.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "program_id")
    private Long programId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserEntity() {}

    public UserEntity(String email, String passwordHash, Long programId) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.programId = programId;
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }

    public String getPasswordHash() { return passwordHash; }

    public Long getProgramId() { return programId; }

    public void setProgramId(Long programId) { this.programId = programId; }
}