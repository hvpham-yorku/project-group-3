package com.yupathbuilder.backend.course_catalog.store;

import com.yupathbuilder.backend.course_catalog.dto.CourseDetailsDto;

public interface CourseDetailsStore {
  CourseDetailsDto getDetails(String courseCode, String season, int year);
}

