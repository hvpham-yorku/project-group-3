package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.schedule.ScheduleService;
import com.yupathbuilder.backend.schedule.dto.BuildScheduleRequest;
import com.yupathbuilder.backend.schedule.dto.BuildScheduleResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for the ScheduleController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService schedule;

    public ScheduleController(ScheduleService schedule) {
        this.schedule = schedule;
    }

    @PostMapping("/build")
    public ResponseEntity<?> build(@Valid @RequestBody BuildScheduleRequest req) {
        var result = schedule.build(req.term(), req.courseCodes());
        if (result.isEmpty()) {
            return ResponseEntity.status(409).body("No non-conflicting schedule found for the selected courses/term.");
        }
        return ResponseEntity.ok(new BuildScheduleResponse(req.term(), result.get()));
    }
}
