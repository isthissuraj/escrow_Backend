package com.escrowpj.escrow.repository;

import com.escrowpj.escrow.entity.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {
}
