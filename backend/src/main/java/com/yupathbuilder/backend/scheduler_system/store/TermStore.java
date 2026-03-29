package com.yupathbuilder.backend.scheduler_system.store;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;

import java.util.List;

public interface TermStore {
    List<TermDto> listTerms();
}

