package com.yupathbuilder.backend.authentication.jwt;

import io.jsonwebtoken.Claims;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Spring Security filter that extracts JWT credentials from the
 * {@code Authorization} header and populates the security context.
 *
 * <p>Requests with missing or invalid tokens continue through the filter chain
 * as unauthenticated so downstream authorization rules can decide how to
 * respond.</p>
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwt;

    public JwtAuthFilter(JwtUtil jwt) {
        this.jwt = jwt;
    }

    /**
     * Attempts to authenticate the request from a bearer token before handing
     * control to the rest of the filter chain.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                Claims claims = jwt.parse(token);

                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                var auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // Invalid or expired tokens must not leak partial authentication state.
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
