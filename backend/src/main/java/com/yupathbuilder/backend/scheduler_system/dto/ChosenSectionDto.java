package com.yupathbuilder.backend.scheduler_system.dto;

/**
 * Chosen section returned in a built schedule response.
 *
 * @param courseCode course identifier
 * @param sectionId selected section identifier
 * @param days comma-separated meeting days
 * @param startTime start time of the representative slot
 * @param endTime end time of the representative slot
 * @param location location of the representative slot
 */
public record ChosenSectionDto(
    String courseCode,
    String sectionId,
    String days,
    String startTime,
    String endTime,
    String location
) {}
