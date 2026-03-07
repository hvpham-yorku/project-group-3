package com.yupathbuilder.backend.auth.model;

public abstract class User {
    private String username;
    private String passwordHash;
    private String email;
    private boolean verified;
    private UserType userType;

    //Constructor for User
    public User(String username, String passwordHash, String email, UserType userType) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.verified = false;
        this.userType = userType;
    }

    // Getters and setters for username, passwordHash, and email
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
