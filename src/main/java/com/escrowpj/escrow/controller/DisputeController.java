package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.entity.Dispute;
import com.escrowpj.escrow.service.DisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disputes")
@RequiredArgsConstructor
public class DisputeController {

    private final DisputeService disputeService;

    @PostMapping
    public Dispute raise(@RequestBody Dispute dispute) {
        return disputeService.raise(dispute);
    }
}
