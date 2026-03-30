package com.yupathbuilder.backend.scheduler_system.store;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;

import java.util.List;

/**
 * Abstraction over sources that provide academic term data.
 */
public interface TermStore {
    /**
     * Returns all academic terms visible to scheduling clients.
     */
    List<TermDto> listTerms();
}

