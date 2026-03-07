package com.yupathbuilder.backend.auth.model;

public class Student extends User {
    private String firstName;
    private String lastName;
    private String studentId;

    // Constructor for Student
    public Student(String username, String passwordHash, String email, String firstName, String lastName, String studentId, UserType userType) {
        super(username, passwordHash, email, userType);
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
    }

    // Getters and setters for firstName, lastName, and studentId
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
