/*package com.yupathbuilder.backend.unit.auth.jwt;

import com.yupathbuilder.backend.authentication.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilUnitTest {

    private static final String SECRET = "THIS_IS_A_DEMO_SECRET_KEY_1234567890_ABCDE";

    @Test
    void generateTokenAndParseRoundTrip() {
        JwtUtil jwtUtil = new JwtUtil(SECRET, 60);

        String token = jwtUtil.generateToken("wamiq@example.com", List.of("ROLE_STUDENT"));
        Claims claims = jwtUtil.parse(token);

        assertEquals("wamiq@example.com", claims.getSubject());
        assertEquals(List.of("ROLE_STUDENT"), claims.get("roles", List.class));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void parseRejectsTamperedToken() {
        JwtUtil jwtUtil = new JwtUtil(SECRET, 60);
        String token = jwtUtil.generateToken("wamiq@example.com", List.of("ROLE_STUDENT"));
        String tampered = token.substring(0, token.length() - 2) + "aa";

        assertThrows(Exception.class, () -> jwtUtil.parse(tampered));
    }
}
*/