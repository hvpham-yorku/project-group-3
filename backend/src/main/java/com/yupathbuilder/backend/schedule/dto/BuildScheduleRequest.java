package com.yupathbuilder.backend.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


/**
 * Service layer component: BuildScheduleRequest.
 *
 * <p>Contains business logic (e.g., searching courses, building schedules, detecting conflicts).
 */

public record BuildScheduleRequest(
        @NotBlank String term,
        @NotEmpty List<String> courseCodes
) {}
