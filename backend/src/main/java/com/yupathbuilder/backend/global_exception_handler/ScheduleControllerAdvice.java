package com.yupathbuilder.backend.global_exception_handler;

import com.yupathbuilder.backend.scheduler_system.controller.ScheduleControllerDb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Translates schedule-building validation failures into consistent HTTP
 * responses for the schedule API.
 */
@RestControllerAdvice(assignableTypes = ScheduleControllerDb.class)
public class ScheduleControllerAdvice {

    /**
     * Maps schedule input and business-rule violations to an HTTP 400 response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Minimal error payload returned to schedule clients.
     */
    public record ErrorResponse(String message) {
    }
}

