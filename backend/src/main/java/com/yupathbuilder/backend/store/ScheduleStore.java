package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.schedule.dto.ScheduleBuildResponse;

import java.util.List;

public interface ScheduleStore {
  ScheduleBuildResponse build(String termString, List<String> courseCodes);
}