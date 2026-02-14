package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.model.Section;
import java.util.List;


/**
 * Repository abstraction / implementation: SectionRepository.
 *
 * <p>Handles data access (file-backed CSV, in-memory storage, etc.).
 */

public interface SectionRepository {
    List<Section> findAll();
    List<Section> findByNormalizedCourseCodeAndTerm(String normalizedCourseCode, String term);
}
