package com.yupathbuilder.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main Spring Boot entry point for the backend application.
 *
 * <p>This class hands control to Spring Boot so the framework can perform
 * component scanning, auto-configuration, and application context startup for
 * the backend API and its supporting infrastructure.</p>
 */
@SpringBootApplication
public class BackendApplication {
    /**
     * Boots the backend application using Spring Boot's standard startup flow.
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
