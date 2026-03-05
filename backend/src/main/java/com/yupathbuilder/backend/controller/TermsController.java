package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.TermDto;
import com.yupathbuilder.backend.repo.TermRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TermsController {

  private final TermRepo termRepo;

  public TermsController(TermRepo termRepo) {
    this.termRepo = termRepo;
  }

  @GetMapping("/terms")
  public List<TermDto> listTerms() {
    return termRepo.findAll().stream()
        .map(t -> new TermDto(t.getId(), t.getSeason().name(), t.getYear()))
        .toList();
  }
}