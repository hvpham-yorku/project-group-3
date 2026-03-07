package com.yupathbuilder.backend.schedule.dto;

public record ChosenSectionDto(
    String courseCode,
    String sectionId,
    String days,
    String startTime,
    String endTime,
    String location
) {}