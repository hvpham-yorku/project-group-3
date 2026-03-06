package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.auth.UserService;
import com.yupathbuilder.backend.checklist.ChecklistService;
import com.yupathbuilder.backend.checklist.dto.ChecklistResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChecklistController {

    private final UserService users;
    private final ChecklistService checklist;

    public ChecklistController(UserService users, ChecklistService checklist) {
        this.users = users;
        this.checklist = checklist;
    }

    // Requiere login porque usa el usuario actual
    @GetMapping("/me/checklist")
    public ResponseEntity<?> myChecklist(Authentication auth) {
        if (auth == null || auth.getName() == null) return ResponseEntity.status(401).body("Unauthorized");

        var userOpt = users.find(auth.getName());
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("Unauthorized");

        var user = userOpt.get();
        if (user.programId() == null) return ResponseEntity.status(400).body("User has no program selected");

        ChecklistResponseDto dto = checklist.byProgramId(user.programId());
        return ResponseEntity.ok(dto);
    }
}