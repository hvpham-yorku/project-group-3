package com.yupathbuilder.backend.scheduler_system.dto;

/**
 * Request payload for schedule building.
 *
 * @param term canonical term string
 * @param courseCodes requested course codes to place into the schedule
 */
public record ScheduleBuildRequest(String term, String[] courseCodes) {}
