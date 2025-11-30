package com.zealix.officehub.platform.rest;

import com.zealix.officehub.platform.dto.OrgRequestCreateDto;
import com.zealix.officehub.platform.model.OrgRequest;
import com.zealix.officehub.platform.service.OrgRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/org-requests")
public class PublicOrgRequestController {

    private final OrgRequestService orgRequestService;

    public PublicOrgRequestController(OrgRequestService orgRequestService) {
        this.orgRequestService = orgRequestService;
    }

    @PostMapping
    public ResponseEntity<OrgRequest> createOrgRequest(@RequestBody OrgRequestCreateDto dto) {
        OrgRequest created = orgRequestService.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}

