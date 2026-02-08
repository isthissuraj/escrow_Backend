package com.escrowpj.escrow.service;

import com.escrowpj.escrow.entity.EscrowTransaction;
import com.escrowpj.escrow.entity.ProjectPhase;
import com.escrowpj.escrow.repository.PhaseRepository;
import com.escrowpj.escrow.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EscrowService {

    private final PhaseRepository phaseRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void approvePhase(Long phaseId) {
        ProjectPhase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new RuntimeException("Phase not found"));

        phase.setStatus("APPROVED");
        phaseRepository.save(phase);

        EscrowTransaction tx = new EscrowTransaction();
        tx.setProjectId(phase.getProjectId());
        tx.setType("RELEASE");
        tx.setAmount(phase.getAmount());

        transactionRepository.save(tx);
    }
}
