package com.yupathbuilder.backend.scheduler_system.service;

import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.scheduler_system.store.ScheduleStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleBuildService {

    private final ScheduleStore scheduleStore;

    public ScheduleBuildService(ScheduleStore scheduleStore) {
        this.scheduleStore = scheduleStore;
    }

    public ScheduleBuildResponse build(String term, List<String> courseCodes) {
        return scheduleStore.build(term, courseCodes);
    }
}
