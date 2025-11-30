package com.zealix.officehub.platform.dto;

public record ToolResponse(
        Long id,
        String code,
        String displayName,
        String description
) {}
