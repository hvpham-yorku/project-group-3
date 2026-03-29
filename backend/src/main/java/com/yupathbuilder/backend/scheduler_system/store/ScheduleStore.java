package com.yupathbuilder.backend.scheduler_system.store;

import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;

import java.util.List;

public interface ScheduleStore {
  ScheduleBuildResponse build(String termString, List<String> courseCodes);
}

