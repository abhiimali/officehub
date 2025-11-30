package com.zealix.officehub.platform.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "org_requests")
public class OrgRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgName;
    private String slug;
    private String adminName;
    private String adminEmail;
    private String plan;          // e.g. "PRO", "BASIC"

    @Column(length = 1000)
    private String requestedToolsCsv; // "ASSHUB,MEETHUB"

    private String status;        // PENDING, APPROVED, REJECTED

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = OffsetDateTime.now();
        if (status == null) status = "PENDING";
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
