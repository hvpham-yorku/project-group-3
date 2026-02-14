package com.yupathbuilder.backend.service;

import com.yupathbuilder.backend.model.Conflict;
import com.yupathbuilder.backend.model.Section;
import com.yupathbuilder.backend.model.Term;
import com.yupathbuilder.backend.model.TermPlan;
import com.yupathbuilder.backend.repo.TermPlanRepository;

import java.util.List;
import java.util.Optional;


/**
 * Service layer component: TermPlanService.
 *
 * <p>Contains business logic (e.g., searching courses, building schedules, detecting conflicts).
 */

public class TermPlanService {

    private final TermPlanRepository repo;
    private final ConflictDetector conflictDetector;

    public TermPlanService(TermPlanRepository repo, ConflictDetector conflictDetector) {
        this.repo = repo;
        this.conflictDetector = conflictDetector;
    }

    public TermPlan createPlan(String name, Term term) {
        TermPlan plan = new TermPlan(name, term);
        return repo.save(plan);
    }

    public List<TermPlan> listPlans() { return repo.findAll(); }

    public Optional<TermPlan> getPlan(String id) { return repo.findById(id); }

    public boolean deletePlan(String id) { return repo.deleteById(id); }

    public List<Conflict> tryAddSection(TermPlan plan, Section incoming) {
        List<Conflict> conflicts = conflictDetector.detectConflicts(plan.getSelectedSections(), incoming);
        if (conflicts.isEmpty()) {
            plan.getSelectedSections().add(incoming);
            repo.save(plan);
        }
        return conflicts;
    }

    public boolean removeSection(TermPlan plan, String sectionKey) {
        if (sectionKey == null) return false;
        boolean removed = plan.getSelectedSections().removeIf(s -> s != null && sectionKey.equals(s.sectionId()));
        if (removed) repo.save(plan);
        return removed;
    }
}
