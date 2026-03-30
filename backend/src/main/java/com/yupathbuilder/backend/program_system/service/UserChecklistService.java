package com.yupathbuilder.backend.program_system.service;

import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.program_system.store.CatalogStore;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Resolves the authenticated user's program checklist.
 *
 * <p>This service couples authentication identity to the program catalog so the
 * controller can return appropriate authorization and validation responses.</p>
 */
@Service
public class UserChecklistService {

    private final UserRepo userRepo;
    private final CatalogStore catalogStore;

    public UserChecklistService(UserRepo userRepo, CatalogStore catalogStore) {
        this.userRepo = userRepo;
        this.catalogStore = catalogStore;
    }

    /**
     * Returns the checklist for the currently authenticated user or an
     * appropriate error response when the user or program context is missing.
     */
    public ResponseEntity<?> getChecklistFor(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        var userOpt = userRepo.findByEmail(auth.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        var user = userOpt.get();
        if (user.getProgramId() == null) {
            return ResponseEntity.status(400).body("User has no program selected");
        }

        return ResponseEntity.ok(catalogStore.checklistByProgramId(user.getProgramId()));
    }
}
