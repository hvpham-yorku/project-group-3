package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.entity.FacultyEntity;
import com.yupathbuilder.backend.entity.ProgramEntity;
import com.yupathbuilder.backend.repo.FacultyRepo;
import com.yupathbuilder.backend.repo.ProgramRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private final FacultyRepo facultyRepo;
    private final ProgramRepo programRepo;

    public ProgramController(FacultyRepo facultyRepo, ProgramRepo programRepo) {
        this.facultyRepo = facultyRepo;
        this.programRepo = programRepo;
    }

    @GetMapping("/faculties")
    public List<FacultyEntity> faculties() {
        return facultyRepo.findAll();
    }

    @GetMapping("/programs")
    public List<ProgramEntity> programs(@RequestParam(required = false) Long facultyId) {
        if (facultyId == null) return programRepo.findAll();
        return programRepo.findByFaculty_IdOrderByNameAsc(facultyId);
    }
}