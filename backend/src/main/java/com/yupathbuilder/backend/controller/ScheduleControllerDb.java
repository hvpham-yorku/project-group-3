package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.schedule.dto.ScheduleBuildRequest;
import com.yupathbuilder.backend.schedule.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleControllerDb {

  private final ScheduleService scheduleService;

  public ScheduleControllerDb(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @PostMapping("/build")
  public ScheduleBuildResponse build(@RequestBody ScheduleBuildRequest req) {
    List<String> codes = req.courseCodes() == null ? List.of() : Arrays.asList(req.courseCodes());
    return scheduleService.build(req.term(), codes);
  }
}