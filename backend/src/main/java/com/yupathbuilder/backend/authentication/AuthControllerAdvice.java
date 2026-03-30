package com.yupathbuilder.backend.authentication;

import com.yupathbuilder.backend.global_exception_handler.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Converts authentication-specific exceptions into API responses with stable
 * HTTP semantics for clients.
 */
@RestControllerAdvice(assignableTypes = AuthController.class)
public class AuthControllerAdvice {

    /**
     * Maps failed credential checks to an HTTP 401 response so clients can
     * distinguish authentication failures from validation errors.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Maps request and business-rule validation failures to HTTP 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Minimal error payload returned by this advice.
     */
    public record ErrorResponse(String message) {}
}
