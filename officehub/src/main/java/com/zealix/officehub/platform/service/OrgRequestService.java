package com.zealix.officehub.platform.service;


import com.zealix.officehub.platform.dto.OrgRequestCreateDto;
import com.zealix.officehub.platform.model.OrgRequest;
import com.zealix.officehub.platform.repository.OrgRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgRequestService {

    private final OrgRequestRepository orgRequestRepository;

    public OrgRequestService(OrgRequestRepository orgRequestRepository) {
        this.orgRequestRepository = orgRequestRepository;
    }

    public OrgRequest createRequest(OrgRequestCreateDto dto) {
        OrgRequest req = new OrgRequest();
        req.setOrgName(dto.orgName());
        req.setSlug(dto.slug());
        req.setAdminName(dto.adminName());
        req.setAdminEmail(dto.adminEmail());
        req.setPlan(dto.plan());
        req.setRequestedToolsCsv(String.join(",", dto.requestedToolCodes()));
        req.setStatus("PENDING");
        return orgRequestRepository.save(req);
    }

    public List<OrgRequest> getPendingRequests() {
        return orgRequestRepository.findByStatus("PENDING");
    }

    public OrgRequest getRequestOrThrow(Long id) {
        return orgRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrgRequest not found"));
    }

    public OrgRequest save(OrgRequest req) {
        return orgRequestRepository.save(req);
    }
}
