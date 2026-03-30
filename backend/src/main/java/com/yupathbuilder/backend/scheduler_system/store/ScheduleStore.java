package com.yupathbuilder.backend.scheduler_system.store;

import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;

import java.util.List;

/**
 * Abstraction over schedule-building implementations.
 */
public interface ScheduleStore {
  /**
   * Builds a schedule for the requested term and course list.
   */
  ScheduleBuildResponse build(String termString, List<String> courseCodes);
}

