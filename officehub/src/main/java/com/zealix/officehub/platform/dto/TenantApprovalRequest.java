package com.zealix.officehub.platform.dto;

import java.util.List;

public record TenantApprovalRequest(
        Long orgRequestId,
        List<Long> toolIds
) {}
