package com.zealix.officehub.platform.service;


import com.zealix.officehub.identity.KeycloakAdminService;
import com.zealix.officehub.platform.dto.TenantApprovalRequest;
import com.zealix.officehub.platform.model.OrgRequest;
import com.zealix.officehub.platform.model.Tenant;
import com.zealix.officehub.platform.model.Tool;
import com.zealix.officehub.platform.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final OrgRequestService orgRequestService;
    private final ToolService toolService;
    private final KeycloakAdminService keycloakAdminService;

    public TenantService(TenantRepository tenantRepository,
                         OrgRequestService orgRequestService,
                         ToolService toolService,
                         KeycloakAdminService keycloakAdminService) {
        this.tenantRepository = tenantRepository;
        this.orgRequestService = orgRequestService;
        this.toolService = toolService;
        this.keycloakAdminService = keycloakAdminService;
    }

    @Transactional
    public Tenant approveOrgRequest(TenantApprovalRequest request) {
        OrgRequest orgReq = orgRequestService.getRequestOrThrow(request.orgRequestId());

        if (!"PENDING".equals(orgReq.getStatus())) {
            throw new IllegalStateException("OrgRequest is not pending");
        }

        // 1. Create Tenant
        Tenant tenant = new Tenant();
        tenant.setName(orgReq.getOrgName());
        tenant.setSlug(orgReq.getSlug());
        tenant.setAdminEmail(orgReq.getAdminEmail());
        tenant.setStatus("ACTIVE");

        List<Tool> tools = toolService.findToolsByIds(request.toolIds());
        tenant.setSubscribedTools(new HashSet<>(tools));

        Tenant savedTenant = tenantRepository.save(tenant);

        // 2. Create realm + ORG_ADMIN in Keycloak
        keycloakAdminService.createRealmAndOrgAdmin(savedTenant, orgReq.getAdminName());

        // 3. Mark OrgRequest as APPROVED
        orgReq.setStatus("APPROVED");
        orgRequestService.save(orgReq);

        // 4. In real system, trigger email to org admin here

        return savedTenant;
    }
}

