package com.escrowpj.escrow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscrowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    // DEPOSIT, RELEASE, REFUND
    private String type;

    private Double amount;

    private LocalDateTime createdAt = LocalDateTime.now();
}
