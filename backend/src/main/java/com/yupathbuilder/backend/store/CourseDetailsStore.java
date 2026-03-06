package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.dto.CourseDetailsDto;

public interface CourseDetailsStore {
  CourseDetailsDto getDetails(String courseCode, String season, int year);
}