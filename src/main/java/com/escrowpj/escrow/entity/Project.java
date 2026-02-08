package com.escrowpj.escrow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Long clientId;

    private Long freelancerId;

    // CREATED, IN_PROGRESS, COMPLETED
    private String status;
}
