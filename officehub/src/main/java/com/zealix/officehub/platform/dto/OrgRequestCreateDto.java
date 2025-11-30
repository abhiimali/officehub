package com.zealix.officehub.platform.dto;

import java.util.List;

public record OrgRequestCreateDto(
        String orgName,
        String slug,
        String adminName,
        String adminEmail,
        String plan,
        List<String> requestedToolCodes
) {}
