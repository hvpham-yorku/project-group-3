package com.yupathbuilder.backend.model;

import java.util.List;

public class Course {

    private String code;
    private String title;
    private double credits;
    private List<String> prerequisites;

    public Course() {
        // constructor vacío requerido por Jackson
    }
    public Course(String code, String title, double credits) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.prerequisites = new java.util.ArrayList<>();
}

    public Course(String code, String title, double credits, List<String> prerequisites) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.prerequisites = prerequisites;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getCredits() { return credits; }
    public void setCredits(double credits) { this.credits = credits; }

    public List<String> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(List<String> prerequisites) { this.prerequisites = prerequisites; }
}
