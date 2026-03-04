package com.escrowpj.escrow.repository;

import com.escrowpj.escrow.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}