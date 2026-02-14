package com.yupathbuilder.backend.model;

import java.time.LocalTime;


/**
 * Domain model: Section.
 *
 * <p>Simple immutable data structures used throughout the backend.
 */

public record Section(
        String sectionId,
        String courseCode,
        String term,
        String instructor,
        String days,        // e.g. "Mon,Wed"
        LocalTime startTime,
        LocalTime endTime,
        String location,
        int capacity
) {
    public String normalizedCourseCode() {
        return courseCode == null ? "" : courseCode.replaceAll("\\s+", "").toUpperCase();
    }
}
