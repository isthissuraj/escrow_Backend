package com.escrowpj.escrow.service;

import com.escrowpj.escrow.dto.CreateProjectRequest;
import com.escrowpj.escrow.entity.*;
import com.escrowpj.escrow.repository.ProjectRepository;
import com.escrowpj.escrow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // CLIENT creates project with milestones
    public Project createProject(String email, CreateProjectRequest request) {

        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!client.getRole().name().equals("CLIENT")) {
            throw new RuntimeException("Only CLIENT can create projects");
        }

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setCategory(request.getCategory());
        project.setBudget(request.getBudget());
        project.setDeadline(LocalDate.parse(request.getDeadline()));
        project.setClient(client);
        project.setFreelancer(null);
        project.setStatus(ProjectStatus.CREATED);

        // Convert milestone DTOs to entities
        List<ProjectPhase> phases = request.getMilestones().stream().map(m -> {

            ProjectPhase phase = new ProjectPhase();
            phase.setName(m.getName());
            phase.setAmount(m.getAmount());
            phase.setDueDate(LocalDate.parse(m.getDueDate()));
            phase.setStatus(PhaseStatus.PENDING);
            phase.setProject(project);

            return phase;

        }).toList();

        project.setMilestones(phases);

        return projectRepository.save(project);
    }

    // CLIENT → view own projects
    public List<Project> getByClient(String email) {

        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return projectRepository.findByClient(client);
    }

    // FREELANCER → view available projects
    public List<Project> getAvailableProjects() {
        return projectRepository.findByFreelancerIsNull();
    }

    // FREELANCER → view assigned projects
    public List<Project> getAssignedProjects(String email) {

        User freelancer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return projectRepository.findByFreelancer(freelancer);
    }
}
