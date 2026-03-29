package com.yupathbuilder.backend.scheduler_system.dto;

import java.util.List;

public record ScheduleBuildResponse(String term, List<ChosenSectionDto> chosenSections) {}
