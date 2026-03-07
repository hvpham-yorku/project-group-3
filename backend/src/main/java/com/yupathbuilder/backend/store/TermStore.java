package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.dto.TermDto;

import java.util.List;

public interface TermStore {
    List<TermDto> listTerms();
}