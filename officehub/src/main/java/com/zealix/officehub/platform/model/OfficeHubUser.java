package com.zealix.officehub.platform.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "officehub_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "keycloak_user_id"}),
                @UniqueConstraint(columnNames = {"tenant_id", "email"})
        })
public class OfficeHubUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "keycloak_user_id", nullable = false)
    private String keycloakUserId;

    @Column(nullable = false)
    private String email;

    private String name;

    @Column(nullable = false)
    private String status = "ACTIVE";

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // getters and setters
}
