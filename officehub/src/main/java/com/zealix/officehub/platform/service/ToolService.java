package com.zealix.officehub.platform.service;

import com.zealix.officehub.platform.dto.ToolResponse;
import com.zealix.officehub.platform.model.Tool;
import com.zealix.officehub.platform.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public List<ToolResponse> getAllTools() {
        return toolRepository.findAll().stream()
                .map(t -> new ToolResponse(t.getId(), t.getCode(), t.getDisplayName(), t.getDescription()))
                .toList();
    }

    public List<Tool> findToolsByIds(List<Long> ids) {
        return toolRepository.findAllById(ids);
    }
}
