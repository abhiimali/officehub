package com.zealix.officehub.platform.service;


import com.zealix.officehub.platform.dto.PlatformLoginRequest;
import com.zealix.officehub.platform.dto.PlatformLoginResponse;
import com.zealix.officehub.common.security.PlatformJwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlatformAuthService {

    private final String superUsername;
    private final String superPassword;
    private final PlatformJwtTokenService tokenService;

    public PlatformAuthService(
            @Value("${officehub.platform.superadmin.username}") String superUsername,
            @Value("${officehub.platform.superadmin.password}") String superPassword,
            PlatformJwtTokenService tokenService
    ) {
        this.superUsername = superUsername;
        this.superPassword = superPassword;
        this.tokenService = tokenService;
    }

    public PlatformLoginResponse login(PlatformLoginRequest request) {
        if (!superUsername.equals(request.username()) || !superPassword.equals(request.password())) {
            throw new RuntimeException("Invalid credentials"); // replace with custom exception
        }

        String token = tokenService.generateToken(superUsername, "SUPERADMIN");
        return new PlatformLoginResponse(token, "Bearer", tokenService.getExpirationSeconds());
    }
}
