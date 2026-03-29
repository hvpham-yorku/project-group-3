package com.yupathbuilder.backend.scheduler_system.controller;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;
import com.yupathbuilder.backend.scheduler_system.service.TermService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TermsController {

  private final TermService termService;

  public TermsController(TermService termService) {
    this.termService = termService;
  }

  @GetMapping("/terms")
  public List<TermDto> listTerms() {
    return termService.listTerms();
  }
}

