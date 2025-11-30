package com.zealix.officehub.platform.rest;


import com.zealix.officehub.platform.dto.ToolResponse;
import com.zealix.officehub.platform.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform/tools")
public class PlatformToolController {

    private final ToolService toolService;

    public PlatformToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    public ResponseEntity<List<ToolResponse>> getAllTools() {
        return ResponseEntity.ok(toolService.getAllTools());
    }
}

