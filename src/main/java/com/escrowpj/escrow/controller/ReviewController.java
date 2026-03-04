package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.dto.ReviewRequest;
import com.escrowpj.escrow.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{projectId}")
    public ResponseEntity<?> review(
            @PathVariable Long projectId,
            @RequestBody ReviewRequest request){

        reviewService.rateFreelancer(
                projectId,
                request.getRating(),
                request.getComment()
        );

        return ResponseEntity.ok("Review added");
    }
}