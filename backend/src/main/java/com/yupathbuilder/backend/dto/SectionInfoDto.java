package com.yupathbuilder.backend.dto;

import java.util.List;

public record SectionInfoDto(String sectionCode, List<MeetingDto> meetings) {}