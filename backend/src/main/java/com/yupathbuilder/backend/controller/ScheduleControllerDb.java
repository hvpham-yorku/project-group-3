package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.schedule.dto.ScheduleBuildRequest;
import com.yupathbuilder.backend.schedule.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.store.ScheduleStore;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleControllerDb {

  private final ScheduleStore scheduleStore;

  public ScheduleControllerDb(ScheduleStore scheduleStore) {
    this.scheduleStore = scheduleStore;
  }

  @PostMapping("/build")
  public ScheduleBuildResponse build(@RequestBody ScheduleBuildRequest req) {
    List<String> codes = req.courseCodes() == null ? List.of() : Arrays.asList(req.courseCodes());
    return scheduleStore.build(req.term(), codes);
  }
}