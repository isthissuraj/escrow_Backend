package com.escrowpj.escrow.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Integer rating;
    private String comment;
}