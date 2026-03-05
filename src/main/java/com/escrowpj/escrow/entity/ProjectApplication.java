package com.escrowpj.escrow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Project project;

    @ManyToOne
    private User freelancer;

    private String description;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}