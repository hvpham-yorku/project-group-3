package com.yupathbuilder.backend.authentication.model;

public class User {

    private Long id;
    private String email;
    private String passwordHash;
    private Long programId;

    public User() {}

    public User(Long id, String email, String passwordHash, Long programId) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.programId = programId;
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }

    public String getPasswordHash() { return passwordHash; }

    public Long getProgramId() { return programId; }
}