package com.zealix.officehub.common.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class PlatformJwtTokenService {

    private final Key signingKey;
    private final long expirationSeconds;

    public PlatformJwtTokenService(
            @Value("${officehub.platform.jwt.secret}") String secret,
            @Value("${officehub.platform.jwt.expiration-seconds:3600}") long expirationSeconds
    ) {

        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(String username, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("type", "platform")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public io.jsonwebtoken.Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}
