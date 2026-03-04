package com.escrowpj.escrow.repository;

import com.escrowpj.escrow.entity.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<ProjectApplication, Long> {

    List<ProjectApplication> findByProjectId(Long projectId);

}