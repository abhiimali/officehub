package com.zealix.officehub.platform.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tenant_tools",
            joinColumns = @JoinColumn(name = "tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id")
    )
    private Set<Tool> subscribedTools = new HashSet<>();

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

}
