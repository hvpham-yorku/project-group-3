package com.yupathbuilder.backend.course_catalog.dto;

import java.util.List;

public record CourseDetailsDto(
    String courseCode,
    String title,
    String description,
    String term,
    List<SectionInfoDto> sections
) {}
