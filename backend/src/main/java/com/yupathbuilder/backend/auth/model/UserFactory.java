package com.yupathbuilder.backend.auth.model;

public class UserFactory {

    // Private constructor to prevent instantiation
    private UserFactory() {}

    public static User createUser(String username, String passwordHash, String email, 
        String firstName, String lastName, String studentId, 
        UserType type) {
        
        if (type.equals(UserType.STUDENT)) {
            return new Student(username, passwordHash, email, firstName, lastName, studentId, UserType.STUDENT);

        }
        else if (type.equals(UserType.ADMIN)) {
            String adminId = "admin-" + username; // Example admin ID generation
            return new Admin(username, passwordHash, email, adminId, UserType.ADMIN);
        }
        else {
            throw new IllegalArgumentException("Invalid user type: " + type);
        }
        
    }
}
