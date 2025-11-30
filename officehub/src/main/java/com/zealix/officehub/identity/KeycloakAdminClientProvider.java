package com.zealix.officehub.identity;

import jakarta.ws.rs.client.ClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakAdminClientProvider {

    @Value("${keycloak.admin.url}")
    private String serverUrl;

    @Value("${keycloak.admin.realm}")
    private String adminRealm;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    public Keycloak getAdminKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(adminRealm)
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminUsername)
                .password(adminPassword)
                .clientId("admin-cli")
                .resteasyClient(ClientBuilder.newClient())
                .build();
    }
}
