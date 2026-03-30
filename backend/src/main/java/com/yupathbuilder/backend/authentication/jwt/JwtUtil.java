package com.yupathbuilder.backend.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Encapsulates JWT creation and validation for the authentication subsystem.
 *
 * <p>Tokens issued by this utility carry the authenticated user identifier as
 * the subject and a simple role claim consumed by the security filter.</p>
 */
public class JwtUtil {

    private final Key key;
    private final long expirationMinutes;

    public JwtUtil(String secret, long expirationMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    /**
     * Generates a signed JWT for the authenticated user.
     */
    public String generateToken(String username, String role) {

        Instant now = Instant.now();
        Instant exp = now.plus(expirationMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    /**
     * Parses and validates a JWT, returning its claims only when signature and
     * expiration checks succeed.
     */
    public Claims parse(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
