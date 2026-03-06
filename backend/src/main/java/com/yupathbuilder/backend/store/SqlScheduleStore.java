package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.schedule.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.service.ScheduleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "app.store", havingValue = "sql", matchIfMissing = true)
public class SqlScheduleStore implements ScheduleStore {

  private final ScheduleService scheduleService;

  public SqlScheduleStore(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @Override
  public ScheduleBuildResponse build(String termString, List<String> courseCodes) {
    return scheduleService.build(termString, courseCodes);
  }
}