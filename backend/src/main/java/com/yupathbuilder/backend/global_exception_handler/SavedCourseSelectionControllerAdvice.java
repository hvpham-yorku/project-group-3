package com.yupathbuilder.backend.global_exception_handler;

import com.yupathbuilder.backend.scheduler_system.controller.SavedCourseSelectionController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralizes validation error handling for saved course selection endpoints.
 */
@RestControllerAdvice(assignableTypes = SavedCourseSelectionController.class)
public class SavedCourseSelectionControllerAdvice {

    /**
     * Converts invalid saved-course operations into an HTTP 400 response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Minimal error payload returned by this controller advice.
     */
    public record ErrorResponse(String message) {
    }
}

