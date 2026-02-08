package com.escrowpj.escrow.repository;

import com.escrowpj.escrow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByClientId(Long clientId);
    List<Project> findByFreelancerId(Long freelancerId);
}
