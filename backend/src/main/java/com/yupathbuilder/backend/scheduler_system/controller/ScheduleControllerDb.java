package com.yupathbuilder.backend.scheduler_system.controller;

import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildRequest;
import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.scheduler_system.service.ScheduleBuildService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleControllerDb {

  private final ScheduleBuildService scheduleBuildService;

  public ScheduleControllerDb(ScheduleBuildService scheduleBuildService) {
    this.scheduleBuildService = scheduleBuildService;
  }

  @PostMapping("/build")
  public ScheduleBuildResponse build(@RequestBody ScheduleBuildRequest req) {
    List<String> codes = req.courseCodes() == null ? List.of() : Arrays.asList(req.courseCodes());
    return scheduleBuildService.build(req.term(), codes);
  }
}

