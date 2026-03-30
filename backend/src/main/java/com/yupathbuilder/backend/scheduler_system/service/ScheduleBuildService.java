package com.yupathbuilder.backend.scheduler_system.service;

import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.scheduler_system.store.ScheduleStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade service for schedule construction.
 *
 * <p>This service delegates to the active schedule store so controllers remain
 * independent of SQL or stub-backed scheduling implementations.</p>
 */
@Service
public class ScheduleBuildService {

    private final ScheduleStore scheduleStore;

    public ScheduleBuildService(ScheduleStore scheduleStore) {
        this.scheduleStore = scheduleStore;
    }

    /**
     * Builds a schedule for the requested term and course list.
     */
    public ScheduleBuildResponse build(String term, List<String> courseCodes) {
        return scheduleStore.build(term, courseCodes);
    }
}
