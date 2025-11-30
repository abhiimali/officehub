package com.zealix.officehub.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final PlatformJwtTokenService platformJwtTokenService;

    public SecurityConfig(PlatformJwtTokenService platformJwtTokenService) {
        this.platformJwtTokenService = platformJwtTokenService;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain platformSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/platform/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/platform/auth/login").permitAll()
                        .anyRequest().hasRole("SUPERADMIN")
                )
                .addFilterBefore(new PlatformJwtFilter(platformJwtTokenService),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // 2) API endpoints for org users (Keycloak JWT)
    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    // 3) Everything else (public)
    @Bean
    @Order(3)
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/public/**", "/error", "/actuator/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
