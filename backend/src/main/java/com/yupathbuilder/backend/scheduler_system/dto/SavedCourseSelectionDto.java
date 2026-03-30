package com.yupathbuilder.backend.scheduler_system.dto;

/**
 * Response payload representing a user's persisted selected course.
 *
 * @param term canonical term string
 * @param courseCode saved course code
 */
public record SavedCourseSelectionDto(String term, String courseCode) {
}

