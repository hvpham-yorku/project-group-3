package com.yupathbuilder.backend.program_system.dto;

import java.util.List;

/**
 * Nested checklist response returned to clients for a single program.
 *
 * @param programId program identifier the checklist belongs to
 * @param years checklist groups partitioned by academic year
 */
public record ChecklistResponseDto(
        Long programId,
        List<YearDto> years
) {
    /**
     * Checklist items for a single academic year.
     *
     * @param year academic year number
     * @param groups grouped requirements within that year
     */
    public record YearDto(int year, List<GroupDto> groups) {}

    /**
     * Requirement group within a checklist year.
     *
     * @param groupName display label for the group
     * @param reqType requirement type such as REQUIRED or ELECTIVE
     * @param courses courses contained in the group
     */
    public record GroupDto(String groupName, String reqType, List<CourseDto> courses) {}

    /**
     * Course entry within a checklist group.
     *
     * @param id course identifier
     * @param courseCode course code shown to users
     * @param title course title
     */
    public record CourseDto(Long id, String courseCode, String title) {}
}
