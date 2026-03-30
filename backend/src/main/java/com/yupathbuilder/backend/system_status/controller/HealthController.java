package com.yupathbuilder.backend.system_status.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes a simple health endpoint for uptime and deployment checks.
 *
 * <p>The response is intentionally lightweight so external monitors can verify
 * that the application is responsive without touching business logic.</p>
 */
@RestController
public class HealthController {

    /**
     * Returns a constant success response when the application is reachable.
     */
    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }
}

