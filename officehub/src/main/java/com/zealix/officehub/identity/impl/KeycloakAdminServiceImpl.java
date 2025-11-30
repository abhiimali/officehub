package com.zealix.officehub.identity.impl;

import com.zealix.officehub.identity.KeycloakAdminClientProvider;
import com.zealix.officehub.identity.KeycloakAdminService;
import com.zealix.officehub.platform.model.Tenant;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final KeycloakAdminClientProvider provider;

    public KeycloakAdminServiceImpl(KeycloakAdminClientProvider provider) {
        this.provider = provider;
    }

    @Override
    public void createRealmAndOrgAdmin(Tenant tenant, String adminName) {
        String realmName = "officehub-" + tenant.getSlug();
        Keycloak keycloak = provider.getAdminKeycloak();

        // 1. Create Realm
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(realmName);
        realm.setEnabled(true);

        keycloak.realms().create(realm);

        // 2. Get Realm Resource
        RealmResource realmResource = keycloak.realm(realmName);

        // 3. Create Client
        createClient(realmResource);

        // 4. Create ORG_ADMIN role
        createRole(realmResource, "ORG_ADMIN");

        // 5. Create user
        String userId = createOrgAdminUser(realmResource, tenant.getAdminEmail(), adminName);

        // 6. Assign role
        assignRoleToUser(realmResource, userId, "ORG_ADMIN");
    }

    private void createClient(RealmResource realmResource) {
        ClientRepresentation client = new ClientRepresentation();
        client.setClientId("officehub-app");
        client.setEnabled(true);
        client.setPublicClient(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setRedirectUris(Collections.singletonList("*"));

        realmResource.clients().create(client);
    }

    private void createRole(RealmResource realmResource, String roleName) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setDescription("Default role for organization admin");

        realmResource.roles().create(role);
    }

    private String createOrgAdminUser(RealmResource realmResource, String email, String name) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(email);
        user.setEmail(email);
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setFirstName(name);

        Response response = realmResource.users().create(user);
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        // Set password
        CredentialRepresentation password = new CredentialRepresentation();
        password.setTemporary(true);
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue("Temp@123");

        realmResource.users().get(userId).resetPassword(password);

        return userId;
    }

    private void assignRoleToUser(RealmResource realm, String userId, String roleName) {
        UserResource userResource = realm.users().get(userId);
        RoleRepresentation role = realm.roles().get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(role));
    }
}
