package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.dto.TermDto;
import com.yupathbuilder.backend.repo.TermRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "app.store", havingValue = "sql", matchIfMissing = true)
public class SqlTermStore implements TermStore {

    private final TermRepo termRepo;

    public SqlTermStore(TermRepo termRepo) {
        this.termRepo = termRepo;
    }

    @Override
    public List<TermDto> listTerms() {
        return termRepo.findAll().stream()
            .map(t -> new TermDto(t.getId(), t.getSeason().name(), t.getYear()))
            .toList();
    }
}