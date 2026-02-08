package com.escrowpj.escrow.service;

import com.escrowpj.escrow.entity.Dispute;
import com.escrowpj.escrow.repository.DisputeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisputeService {

    private final DisputeRepository disputeRepository;

    public Dispute raise(Dispute dispute) {
        dispute.setStatus("OPEN");
        return disputeRepository.save(dispute);
    }
}
