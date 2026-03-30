package com.yupathbuilder.backend.scheduler_system.controller;

import com.yupathbuilder.backend.scheduler_system.dto.SaveCourseSelectionRequest;
import com.yupathbuilder.backend.scheduler_system.dto.SavedCourseSelectionDto;
import com.yupathbuilder.backend.scheduler_system.service.SavedCourseSelectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exposes endpoints for persisting and managing a user's selected courses.
 */
@RestController
@RequestMapping("/api/me/selected-courses")
public class SavedCourseSelectionController {

    private final SavedCourseSelectionService savedCourseSelectionService;

    public SavedCourseSelectionController(SavedCourseSelectionService savedCourseSelectionService) {
        this.savedCourseSelectionService = savedCourseSelectionService;
    }

    /**
     * Returns the current authenticated user's saved course selections.
     */
    @GetMapping
    public List<SavedCourseSelectionDto> list(Authentication auth) {
        return savedCourseSelectionService.listSelections(auth.getName());
    }

    /**
     * Saves a course selection for the current user and returns the persisted
     * representation.
     */
    @PostMapping
    public ResponseEntity<SavedCourseSelectionDto> save(
            Authentication auth,
            @RequestBody SaveCourseSelectionRequest request
    ) {
        SavedCourseSelectionDto saved = savedCourseSelectionService.saveSelection(
                auth.getName(),
                request.term(),
                request.courseCode()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Removes a previously saved course selection for the current user.
     */
    @DeleteMapping
    public ResponseEntity<Void> remove(
            Authentication auth,
            @RequestParam String term,
            @RequestParam String courseCode
    ) {
        savedCourseSelectionService.removeSelection(auth.getName(), term, courseCode);
        return ResponseEntity.noContent().build();
    }
}


