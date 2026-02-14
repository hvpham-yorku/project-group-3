package com.yupathbuilder.backend.model;

import jakarta.validation.constraints.NotBlank;


/**
 * Domain model: Course.
 *
 * <p>Simple immutable data structures used throughout the backend.
 */

public record Course(
        @NotBlank String courseCode,
        String title,
        double credits,
        String department,
        String prerequisites
) {
    public String normalizedCode() {
        return courseCode == null ? "" : courseCode.replaceAll("\\s+", "").toUpperCase();
    }
}
