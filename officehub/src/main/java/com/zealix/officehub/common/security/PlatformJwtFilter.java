package com.zealix.officehub.common.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class PlatformJwtFilter extends OncePerRequestFilter {

    private final PlatformJwtTokenService tokenService;

    public PlatformJwtFilter(PlatformJwtTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Only protect /platform/**
        return !path.startsWith("/platform");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = tokenService.parse(token);
                String role = claims.get("role", String.class);
                var auth = new AbstractAuthenticationToken(
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))) {
                    @Override
                    public Object getCredentials() {
                        return token;
                    }

                    @Override
                    public Object getPrincipal() {
                        return claims.getSubject();
                    }
                };
                auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // invalid token -> clear context, let Spring handle 401
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
