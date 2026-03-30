package com.yupathbuilder.backend.course_catalog.dto;

/**
 * Transport representation of a single scheduled meeting within a section.
 *
 * @param day abbreviated day-of-week label
 * @param startTime meeting start time
 * @param endTime meeting end time
 * @param location room or location label
 */
public record MeetingDto(String day, String startTime, String endTime, String location) {}
