package com.yupathbuilder.backend.repo.memory;

import com.yupathbuilder.backend.model.TermPlan;
import com.yupathbuilder.backend.repo.TermPlanRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


/**
 * Repository abstraction / implementation: InMemoryTermPlanRepository.
 *
 * <p>Handles data access (file-backed CSV, in-memory storage, etc.).
 */

@Repository
public class InMemoryTermPlanRepository implements TermPlanRepository {

    private final Map<String, TermPlan> plans = new HashMap<>();

    @Override
    public List<TermPlan> findAll() {
        return new ArrayList<>(plans.values());
    }

    @Override
    public Optional<TermPlan> findById(String id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(plans.get(id));
    }

    @Override
    public TermPlan save(TermPlan plan) {
        if (plan == null) return null;
        if (plan.getId() == null || plan.getId().trim().isEmpty()) {
            plan.setId(UUID.randomUUID().toString());
        }
        plans.put(plan.getId(), plan);
        return plan;
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) return false;
        return plans.remove(id) != null;
    }
}
