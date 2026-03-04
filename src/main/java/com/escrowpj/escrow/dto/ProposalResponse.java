package com.escrowpj.escrow.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalResponse {

    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String status;
}