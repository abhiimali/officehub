package com.zealix.officehub.platform.repository;


import com.zealix.officehub.platform.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    Optional<Tool> findByCode(String code);
    List<Tool> findAll();
}

