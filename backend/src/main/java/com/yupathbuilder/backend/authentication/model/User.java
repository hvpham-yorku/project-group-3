package com.yupathbuilder.backend.authentication.model;

import com.yupathbuilder.backend.authentication.UserRole;

public class User {

    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private Long programId;
    private UserRole role;

    public User() {}

    public User(String username,
                String passwordHash,
                String firstName,
                String lastName,
                Long programId,
                UserRole role) {

        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.programId = programId;
        this.role = role;
    }

    public String getUsername() { return username; }

    public String getPasswordHash() { return passwordHash; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public Long getProgramId() { return programId; }

    public UserRole getType() { return role; }
}