package com.yupathbuilder.backend.program_system.controller;

import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.service.ProgramCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private final ProgramCatalogService programCatalogService;

    public ProgramController(ProgramCatalogService programCatalogService) {
        this.programCatalogService = programCatalogService;
    }

    @GetMapping("/faculties")
    public List<FacultyEntity> faculties() {
        return programCatalogService.listFaculties();
    }

    @GetMapping("/programs")
    public List<ProgramEntity> programs(@RequestParam(required = false) Long facultyId) {
        return programCatalogService.listPrograms(facultyId);
    }
}

