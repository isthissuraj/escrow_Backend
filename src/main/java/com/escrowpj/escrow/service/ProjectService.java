package com.escrowpj.escrow.service;

import com.escrowpj.escrow.entity.Project;
import com.escrowpj.escrow.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project create(Project project) {
        project.setStatus("CREATED");
        return projectRepository.save(project);
    }

    public List<Project> getByClient(Long clientId) {
        return projectRepository.findByClientId(clientId);
    }
}
