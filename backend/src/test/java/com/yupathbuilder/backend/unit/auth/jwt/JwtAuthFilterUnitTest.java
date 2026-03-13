package com.yupathbuilder.backend.unit.auth.jwt;

import com.yupathbuilder.backend.authentication.jwt.JwtAuthFilter;
import com.yupathbuilder.backend.authentication.jwt.JwtUtil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthFilterUnitTest {

    private static final String SECRET = "THIS_IS_A_DEMO_SECRET_KEY_1234567890_ABCDE";

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterSetsAuthenticationWhenTokenValid() throws Exception {

        JwtUtil jwtUtil = new JwtUtil(SECRET, 60);
        JwtAuthFilter filter = new JwtAuthFilter(jwtUtil);

        String token = jwtUtil.generateToken(
                "wamiq@example.com",
                "ROLE_STUDENT"
        );

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        var auth = SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(auth);
        assertEquals("wamiq@example.com", auth.getName());
    }

    @Test
    void doFilterLeavesRequestAnonymousWhenTokenInvalid() throws Exception {

        JwtUtil jwtUtil = new JwtUtil(SECRET, 60);
        JwtAuthFilter filter = new JwtAuthFilter(jwtUtil);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");

        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}