package com.zealix.officehub.platform.dto;

public record PlatformLoginResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds
) {}
