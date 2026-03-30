package com.yupathbuilder.backend.course_catalog.dto;

import java.util.List;

/**
 * Detailed course representation returned by the course details endpoint.
 *
 * @param courseCode course identifier
 * @param title course title
 * @param description course description
 * @param term user-facing academic term string
 * @param sections section and meeting information for the requested term
 */
public record CourseDetailsDto(
    String courseCode,
    String title,
    String description,
    String term,
    List<SectionInfoDto> sections
) {}
