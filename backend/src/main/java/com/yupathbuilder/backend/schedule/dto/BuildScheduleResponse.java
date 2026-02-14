package com.yupathbuilder.backend.schedule.dto;

import com.yupathbuilder.backend.model.Section;

import java.util.List;


/**
 * Service layer component: BuildScheduleResponse.
 *
 * <p>Contains business logic (e.g., searching courses, building schedules, detecting conflicts).
 */

public record BuildScheduleResponse(
        String term,
        List<Section> chosenSections
) {}
