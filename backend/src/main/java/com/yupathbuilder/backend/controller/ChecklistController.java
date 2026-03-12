package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.store.CatalogStore;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChecklistController {

    private final UserRepo repo;
    private final CatalogStore store;

    public ChecklistController(UserRepo repo, CatalogStore store) {
        this.repo = repo;
        this.store = store;
    }

    @GetMapping("/me/checklist")
    public ResponseEntity<?> myChecklist(Authentication auth) {

        if (auth == null || auth.getName() == null)
            return ResponseEntity.status(401).body("Unauthorized");

        var userOpt = repo.findByEmail(auth.getName());

        if (userOpt.isEmpty())
            return ResponseEntity.status(401).body("Unauthorized");

        var user = userOpt.get();

        if (user.getProgramId() == null)
            return ResponseEntity.status(400).body("User has no program selected");

        return ResponseEntity.ok(store.checklistByProgramId(user.getProgramId()));
    }
}