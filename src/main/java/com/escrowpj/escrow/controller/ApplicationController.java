package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.dto.*;
import com.escrowpj.escrow.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply/{projectId}")
    public ResponseEntity<?> applyProject(
            @PathVariable Long projectId,
            @RequestBody Map<String,String> body,
            Principal principal
    ){

        applicationService.apply(
                projectId,
                principal.getName(),
                body.get("description")
        );

        return ResponseEntity.ok("Applied successfully");
    }

    @GetMapping("/proposal/{projectId}")
    public ResponseEntity<?> getProposals(@PathVariable Long projectId){

        return ResponseEntity.ok(
                applicationService.getProposals(projectId)
        );
    }

    @PostMapping("/proposal/{id}/accept")
    public ResponseEntity<?> acceptProposal(@PathVariable Long id){

        applicationService.assignFreelancer(id);

        return ResponseEntity.ok("Freelancer assigned");
    }
}