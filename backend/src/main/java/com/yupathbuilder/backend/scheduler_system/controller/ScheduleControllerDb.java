package com.yupathbuilder.backend.scheduler_system.controller;

import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildRequest;
import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.scheduler_system.service.ScheduleBuildService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Exposes schedule-building endpoints for authenticated clients.
 */
@RestController
@RequestMapping("/api/schedule")
public class ScheduleControllerDb {

  private final ScheduleBuildService scheduleBuildService;

  public ScheduleControllerDb(ScheduleBuildService scheduleBuildService) {
    this.scheduleBuildService = scheduleBuildService;
  }

  /**
   * Builds a schedule for the requested term and set of course codes.
   */
  @PostMapping("/build")
  public ScheduleBuildResponse build(@RequestBody ScheduleBuildRequest req) {
    List<String> codes = req.courseCodes() == null ? List.of() : Arrays.asList(req.courseCodes());
    return scheduleBuildService.build(req.term(), codes);
  }
}

