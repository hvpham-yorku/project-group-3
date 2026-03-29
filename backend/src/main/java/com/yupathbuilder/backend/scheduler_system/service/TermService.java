package com.yupathbuilder.backend.scheduler_system.service;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;
import com.yupathbuilder.backend.scheduler_system.store.TermStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {

    private final TermStore termStore;

    public TermService(TermStore termStore) {
        this.termStore = termStore;
    }

    public List<TermDto> listTerms() {
        return termStore.listTerms();
    }
}
