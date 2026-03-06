package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.TermDto;
import com.yupathbuilder.backend.store.TermStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TermsController {

  private final TermStore termStore;

  public TermsController(TermStore termStore) {
    this.termStore = termStore;
  }

  @GetMapping("/terms")
  public List<TermDto> listTerms() {
    return termStore.listTerms();
  }
}