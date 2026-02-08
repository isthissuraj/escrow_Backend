package com.escrowpj.escrow.repository;

import com.escrowpj.escrow.entity.EscrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<EscrowTransaction, Long> {
}
