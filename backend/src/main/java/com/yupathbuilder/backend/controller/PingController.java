package com.yupathbuilder.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for the PingController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
public class PingController {
    @GetMapping("/api/ping")
    public String ping() {
        return "pong";
    }
}
