package com.yupathbuilder.backend.scheduler_system.store;

import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.scheduler_system.service.ScheduleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!stub")
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

