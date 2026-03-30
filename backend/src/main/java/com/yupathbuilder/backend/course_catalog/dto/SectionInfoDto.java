package com.yupathbuilder.backend.course_catalog.dto;

import java.util.List;

/**
 * Transport representation of a course section and its meetings.
 *
 * @param sectionCode section identifier visible to users
 * @param meetings scheduled meetings belonging to the section
 */
public record SectionInfoDto(String sectionCode, List<MeetingDto> meetings) {}
