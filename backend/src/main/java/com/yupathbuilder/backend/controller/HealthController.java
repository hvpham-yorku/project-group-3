package com.yupathbuilder.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for the HealthController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }
}
