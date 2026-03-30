package com.yupathbuilder.backend.course_catalog.dto;

/**
 * Lightweight course summary returned by catalog listing and search endpoints.
 *
 * @param courseCode course identifier shown to users
 * @param title human-readable course title
 * @param description short course description when available
 */
public record CourseDto(String courseCode, String title, String description) {}

