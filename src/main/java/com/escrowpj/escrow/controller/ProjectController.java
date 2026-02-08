package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.entity.Project;
import com.escrowpj.escrow.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @GetMapping("/client/{clientId}")
    public List<Project> getByClient(@PathVariable Long clientId) {
        return projectService.getByClient(clientId);
    }
}
