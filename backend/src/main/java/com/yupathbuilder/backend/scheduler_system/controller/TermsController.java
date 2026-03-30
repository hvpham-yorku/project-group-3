package com.yupathbuilder.backend.scheduler_system.controller;

import com.yupathbuilder.backend.scheduler_system.dto.TermDto;
import com.yupathbuilder.backend.scheduler_system.service.TermService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exposes the academic terms available for scheduling operations.
 */
@RestController
@RequestMapping("/api")
public class TermsController {

  private final TermService termService;

  public TermsController(TermService termService) {
    this.termService = termService;
  }

  /**
   * Returns the academic terms available in the active term store.
   */
  @GetMapping("/terms")
  public List<TermDto> listTerms() {
    return termService.listTerms();
  }
}

