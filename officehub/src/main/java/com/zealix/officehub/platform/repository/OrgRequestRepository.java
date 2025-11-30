package com.zealix.officehub.platform.repository;

import com.zealix.officehub.platform.model.OrgRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgRequestRepository extends JpaRepository<OrgRequest, Long> {
    List<OrgRequest> findByStatus(String status);
}
