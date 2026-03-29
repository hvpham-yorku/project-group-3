package com.yupathbuilder.backend.program_system.dto;

import java.util.List;

public record ChecklistResponseDto(
        Long programId,
        List<YearDto> years
) {
    public record YearDto(int year, List<GroupDto> groups) {}
    public record GroupDto(String groupName, String reqType, List<CourseDto> courses) {}
    public record CourseDto(Long id, String courseCode, String title) {}
}
