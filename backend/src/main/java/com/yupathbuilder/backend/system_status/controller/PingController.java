package com.yupathbuilder.backend.system_status.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes a lightweight ping endpoint used for connectivity checks and quick
 * smoke tests.
 *
 * <p>This endpoint is useful during frontend integration because it verifies
 * that the HTTP layer is reachable without depending on database-backed
 * features.</p>
 */
@RestController
public class PingController {
    /**
     * Returns a constant response that confirms the API is reachable.
     */
    @GetMapping("/api/ping")
    public String ping() {
        return "pong";
    }
}

