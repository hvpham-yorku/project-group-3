package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.model.Conflict;
import com.yupathbuilder.backend.model.Section;
import com.yupathbuilder.backend.model.Term;
import com.yupathbuilder.backend.model.TermPlan;
import com.yupathbuilder.backend.service.TermPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


/**
 * REST controller for the TermPlanController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
@RequestMapping("/api/plans")
public class TermPlanController {

    private final TermPlanService service;

    public TermPlanController(TermPlanService service) {
        this.service = service;
    }

    public static class CreatePlanRequest {
        public String name;
        public Term.Season season;
        public int year;
    }

    public static class AddSectionRequest {
        public Section section;
    }

    @GetMapping
    public List<TermPlan> list() {
        return service.listPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermPlan> get(@PathVariable String id) {
        return service.getPlan(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreatePlanRequest req) {
        if (req == null || req.season == null || req.year == 0) {
            return ResponseEntity.badRequest().body("Missing term fields: season, year");
        }
        String name = (req.name == null || req.name.trim().isEmpty()) ? "Main Plan" : req.name.trim();
        TermPlan plan = service.createPlan(name, new Term(req.season, req.year));
        return ResponseEntity.created(URI.create("/api/plans/" + plan.getId())).body(plan);
    }

    @PostMapping("/{id}/sections")
    public ResponseEntity<?> addSection(@PathVariable String id, @RequestBody AddSectionRequest req) {
        if (req == null || req.section == null) return ResponseEntity.badRequest().body("Missing section");
        return service.getPlan(id).map(plan -> {
            List<Conflict> conflicts = service.tryAddSection(plan, req.section);
            if (!conflicts.isEmpty()) return ResponseEntity.status(409).body(conflicts);
            return ResponseEntity.ok(plan);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/sections/{sectionKey}")
    public ResponseEntity<?> removeSection(@PathVariable String id, @PathVariable String sectionKey) {
        return service.getPlan(id).map(plan -> {
            boolean removed = service.removeSection(plan, sectionKey);
            return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return service.deletePlan(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
