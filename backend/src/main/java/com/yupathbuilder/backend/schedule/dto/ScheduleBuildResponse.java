package com.yupathbuilder.backend.schedule.dto;

import java.util.List;

public record ScheduleBuildResponse(String term, List<ChosenSectionDto> chosenSections) {}