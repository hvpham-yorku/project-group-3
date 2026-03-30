package com.yupathbuilder.backend.program_system.controller;

import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.service.ProgramCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exposes faculty and program catalog endpoints used during onboarding and
 * profile management.
 */
@RestController
@RequestMapping("/api")
public class ProgramController {

    private final ProgramCatalogService programCatalogService;

    public ProgramController(ProgramCatalogService programCatalogService) {
        this.programCatalogService = programCatalogService;
    }

    /**
     * Returns the list of faculties available in the active catalog source.
     */
    @GetMapping("/faculties")
    public List<FacultyEntity> faculties() {
        return programCatalogService.listFaculties();
    }

    /**
     * Returns programs, optionally filtered to a single faculty.
     */
    @GetMapping("/programs")
    public List<ProgramEntity> programs(@RequestParam(required = false) Long facultyId) {
        return programCatalogService.listPrograms(facultyId);
    }
}

