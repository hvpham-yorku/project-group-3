package com.yupathbuilder.backend.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pure JUnit test: calls controller method directly.
 */
class HealthControllerUnitTest {

    @Test
    void health_returnsOk() {
        HealthController controller = new HealthController();

        String result = controller.health(); // <-- adjust if your method name differs

        assertEquals("OK", result);
    }
}
