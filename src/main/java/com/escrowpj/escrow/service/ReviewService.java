package com.escrowpj.escrow.service;

import com.escrowpj.escrow.entity.*;
import com.escrowpj.escrow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public void rateFreelancer(Long projectId, int rating, String comment){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if(project.getStatus()!=ProjectStatus.COMPLETED){
            throw new RuntimeException("Project not completed yet");
        }

        Review review = new Review();

        review.setProject(project);
        review.setFreelancer(project.getFreelancer());
        review.setRating(rating);
        review.setComment(comment);

        reviewRepository.save(review);

        User freelancer = project.getFreelancer();

        double newRating =
                ((freelancer.getRating()*freelancer.getTotalReviews()) + rating)
                        /(freelancer.getTotalReviews()+1);

        freelancer.setRating(newRating);
        freelancer.setTotalReviews(freelancer.getTotalReviews()+1);

        userRepository.save(freelancer);
    }

}