package com.escrowpj.escrow.service;

import com.escrowpj.escrow.dto.ProposalResponse;
import com.escrowpj.escrow.entity.*;
import com.escrowpj.escrow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void apply(Long projectId, String email, String description) {

        User freelancer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if(project.getFreelancer()!=null){
            throw new RuntimeException("Project already assigned");
        }

        ProjectApplication application = new ProjectApplication();

        application.setProject(project);
        application.setFreelancer(freelancer);
        application.setDescription(description);
        application.setStatus(ApplicationStatus.APPLIED);

        applicationRepository.save(application);
    }

    public List<ProposalResponse> getProposals(Long projectId){

        List<ProjectApplication> apps =
                applicationRepository.findByProjectId(projectId);

        return apps.stream().map(app ->

                new ProposalResponse(
                        app.getId(),
                        app.getFreelancer().getName(),
                        app.getDescription(),
                        app.getFreelancer().getRating(),
                        app.getStatus().name()
                )

        ).toList();
    }

    @Transactional
    public void assignFreelancer(Long applicationId){

        ProjectApplication app =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new RuntimeException("Proposal not found"));

        Project project = app.getProject();

        if(project.getFreelancer()!=null){
            throw new RuntimeException("Project already assigned");
        }

        project.setFreelancer(app.getFreelancer());
        project.setStatus(ProjectStatus.IN_PROGRESS);

        app.setStatus(ApplicationStatus.ACCEPTED);

        projectRepository.save(project);
    }

}