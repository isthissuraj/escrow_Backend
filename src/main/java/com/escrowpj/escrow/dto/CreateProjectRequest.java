package com.escrowpj.escrow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProjectRequest {

    private String title;
    private String description;
    private String category;
    private Double budget;
    private String deadline; // "2026-06-01"

    private List<MilestoneRequest> milestones;

    @Getter
    @Setter
    public static class MilestoneRequest {
        private String name;
        private Double amount;
        private String dueDate; // "2026-05-10"
    }
}
