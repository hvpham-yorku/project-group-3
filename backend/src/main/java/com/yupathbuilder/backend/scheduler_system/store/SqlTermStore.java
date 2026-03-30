package com.yupathbuilder.backend.scheduler_system.store;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;
import com.yupathbuilder.backend.scheduler_system.repo.TermRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SQL-backed implementation of the term store.
 */
@Component
@ConditionalOnProperty(name = "app.store", havingValue = "sql", matchIfMissing = true)
public class SqlTermStore implements TermStore {

    private final TermRepo termRepo;

    public SqlTermStore(TermRepo termRepo) {
        this.termRepo = termRepo;
    }

    /**
     * Maps persisted term entities into transport DTOs for the API.
     */
    @Override
    public List<TermDto> listTerms() {
        return termRepo.findAll().stream()
            .map(t -> new TermDto(t.getId(), t.getSeason().name(), t.getYear()))
            .toList();
    }
}

