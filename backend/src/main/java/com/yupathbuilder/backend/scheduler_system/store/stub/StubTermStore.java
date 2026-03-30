package com.yupathbuilder.backend.scheduler_system.store.stub;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;
import com.yupathbuilder.backend.scheduler_system.store.TermStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Stub-backed term store that returns a fixed set of academic terms.
 */
@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubTermStore implements TermStore {

    /**
     * Returns the hard-coded term list used in stub mode.
     */
    @Override
    public List<TermDto> listTerms() {
        return List.of(
            new TermDto(1L, "FALL", 2026),
            new TermDto(2L, "WINTER", 2027)
        );
    }
}

