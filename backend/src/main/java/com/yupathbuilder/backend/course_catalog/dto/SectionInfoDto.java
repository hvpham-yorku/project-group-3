package com.yupathbuilder.backend.course_catalog.dto;

import java.util.List;

public record SectionInfoDto(String sectionCode, List<MeetingDto> meetings) {}
