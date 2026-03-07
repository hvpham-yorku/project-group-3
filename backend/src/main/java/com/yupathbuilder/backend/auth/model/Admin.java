package com.yupathbuilder.backend.auth.model;

public class Admin extends User {
    private String adminId;

    // Constructor for Admin
    public Admin(String username, String passwordHash, String email, String adminId, UserType userType) {
        super(username, passwordHash, email, userType);
        this.adminId = adminId;
    }

    // Getters and setters for adminId
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

}
