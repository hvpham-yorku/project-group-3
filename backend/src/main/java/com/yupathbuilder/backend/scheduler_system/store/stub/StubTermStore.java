package com.yupathbuilder.backend.scheduler_system.store.stub;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;
import com.yupathbuilder.backend.scheduler_system.store.TermStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubTermStore implements TermStore {

    @Override
    public List<TermDto> listTerms() {
        return List.of(
            new TermDto(1L, "FALL", 2026),
            new TermDto(2L, "WINTER", 2027)
        );
    }
}

