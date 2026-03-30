package com.yupathbuilder.backend.scheduler_system.dto;

/**
 * Request payload for saving a selected course for a user.
 *
 * @param term canonical term string such as {@code FALL 2026}
 * @param courseCode course code to save
 */
public record SaveCourseSelectionRequest(String term, String courseCode) {
}

