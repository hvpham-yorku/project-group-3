package com.yupathbuilder.backend.global_exception_handler;

import com.yupathbuilder.backend.scheduler_system.controller.ScheduleControllerDb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ScheduleControllerDb.class)
public class ScheduleControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) {
    }
}

