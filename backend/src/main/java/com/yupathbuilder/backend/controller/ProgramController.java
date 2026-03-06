package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.entity.FacultyEntity;
import com.yupathbuilder.backend.entity.ProgramEntity;
import com.yupathbuilder.backend.store.CatalogStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private final CatalogStore store;

    public ProgramController(CatalogStore store) {
        this.store = store;
    }

    @GetMapping("/faculties")
    public List<FacultyEntity> faculties() {
        return store.listFaculties();
    }

    @GetMapping("/programs")
    public List<ProgramEntity> programs(@RequestParam(required = false) Long facultyId) {
        return store.listPrograms(facultyId);
    }
}