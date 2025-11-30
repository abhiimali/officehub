package com.zealix.officehub.platform.rest;

import com.zealix.officehub.platform.dto.TenantApprovalRequest;
import com.zealix.officehub.platform.model.OrgRequest;
import com.zealix.officehub.platform.model.Tenant;
import com.zealix.officehub.platform.service.OrgRequestService;
import com.zealix.officehub.platform.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform/tenants")
public class PlatformTenantController {

    private final OrgRequestService orgRequestService;
    private final TenantService tenantService;

    public PlatformTenantController(OrgRequestService orgRequestService,
                                    TenantService tenantService) {
        this.orgRequestService = orgRequestService;
        this.tenantService = tenantService;
    }

    @GetMapping("/requests/pending")
    public ResponseEntity<List<OrgRequest>> getPendingRequests() {
        return ResponseEntity.ok(orgRequestService.getPendingRequests());
    }

    @PostMapping("/approve")
    public ResponseEntity<Tenant> approveOrgRequest(@RequestBody TenantApprovalRequest request) {
        Tenant tenant = tenantService.approveOrgRequest(request);
        return ResponseEntity.ok(tenant);
    }
}

