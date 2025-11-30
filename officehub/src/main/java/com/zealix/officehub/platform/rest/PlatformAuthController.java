package com.zealix.officehub.platform.rest;

import com.zealix.officehub.platform.dto.PlatformLoginRequest;
import com.zealix.officehub.platform.dto.PlatformLoginResponse;
import com.zealix.officehub.platform.service.PlatformAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platform/auth")
public class PlatformAuthController {

    private final PlatformAuthService platformAuthService;

    public PlatformAuthController(PlatformAuthService platformAuthService) {
        this.platformAuthService = platformAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<PlatformLoginResponse> login(@RequestBody PlatformLoginRequest request) {
        return ResponseEntity.ok(platformAuthService.login(request));
    }
}

