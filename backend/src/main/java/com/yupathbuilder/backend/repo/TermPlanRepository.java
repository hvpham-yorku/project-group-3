package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.model.TermPlan;

import java.util.List;
import java.util.Optional;


/**
 * Repository abstraction / implementation: TermPlanRepository.
 *
 * <p>Handles data access (file-backed CSV, in-memory storage, etc.).
 */

public interface TermPlanRepository {
    List<TermPlan> findAll();
    Optional<TermPlan> findById(String id);
    TermPlan save(TermPlan plan);
    boolean deleteById(String id);
}
