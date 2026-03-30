package com.yupathbuilder.backend.global_exception_handler;

/**
 * Signals that an authentication attempt failed because the supplied
 * credentials could not be verified.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Creates a new exception with the client-safe error message to expose.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
