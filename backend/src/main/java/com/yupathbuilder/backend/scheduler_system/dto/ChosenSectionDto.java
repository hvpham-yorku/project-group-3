package com.yupathbuilder.backend.scheduler_system.dto;

public record ChosenSectionDto(
    String courseCode,
    String sectionId,
    String days,
    String startTime,
    String endTime,
    String location
) {}
