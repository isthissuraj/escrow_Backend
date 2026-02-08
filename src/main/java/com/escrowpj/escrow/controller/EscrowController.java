package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.service.EscrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/escrow")
@RequiredArgsConstructor
public class EscrowController {

    private final EscrowService escrowService;

    @PostMapping("/approve/{phaseId}")
    public String approve(@PathVariable Long phaseId) {
        escrowService.approvePhase(phaseId);
        return "Phase approved and payment released";
    }
}
