package com.escrowpj.escrow.repository;

import com.escrowpj.escrow.entity.Project;
import com.escrowpj.escrow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // CLIENT → their own projects
    List<Project> findByClient(User client);

    // FREELANCER → assigned projects
    List<Project> findByFreelancer(User freelancer);

    // FREELANCER → available projects
    List<Project> findByFreelancerIsNull();
}