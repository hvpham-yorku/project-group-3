package com.yupathbuilder.backend.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pure JUnit test: calls controller method directly (no Spring, no HTTP).
 */
class PingControllerUnitTest {

    @Test
    void ping_returnsPong() {
        PingController controller = new PingController();

        String result = controller.ping(); // <-- adjust if your method name differs

        assertEquals("pong", result);
    }
}
