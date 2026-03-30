package com.yupathbuilder.backend.scheduler_system.dto;

import java.util.List;

/**
 * Response payload returned after schedule construction.
 *
 * @param term requested academic term
 * @param chosenSections selected non-conflicting sections
 */
public record ScheduleBuildResponse(String term, List<ChosenSectionDto> chosenSections) {}
