package com.zealix.officehub.identity;

import com.zealix.officehub.platform.model.Tenant;

public interface KeycloakAdminService {

    void createRealmAndOrgAdmin(Tenant tenant, String adminName);

}
