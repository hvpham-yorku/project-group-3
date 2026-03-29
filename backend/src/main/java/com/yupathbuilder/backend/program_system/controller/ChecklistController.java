package com.yupathbuilder.backend.program_system.controller;

import com.yupathbuilder.backend.program_system.service.UserChecklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChecklistController {

    private final UserChecklistService userChecklistService;

    public ChecklistController(UserChecklistService userChecklistService) {
        this.userChecklistService = userChecklistService;
    }

    @GetMapping("/me/checklist")
    public ResponseEntity<?> myChecklist(Authentication auth) {
        return userChecklistService.getChecklistFor(auth);
    }
}

