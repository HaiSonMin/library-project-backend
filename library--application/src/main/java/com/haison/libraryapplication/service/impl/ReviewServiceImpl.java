package com.haison.libraryapplication.service.impl;

import com.haison.libraryapplication.repository.ReviewRepository;
import com.haison.libraryapplication.dto.ReviewDTO;
import com.haison.libraryapplication.entity.Review;
import com.haison.libraryapplication.requestModels.ReviewBookRequest;
import com.haison.libraryapplication.service.ReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service(value = "reviewServiceImpl")
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ModelMapper modelMapper;

    @Override
    public void createReview(String userEmail, ReviewBookRequest reviewBookRequest) throws Exception {

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserEmail(userEmail);
        reviewDTO.setBookId(reviewBookRequest.getBookId());
        reviewDTO.setRating(reviewBookRequest.getRating());
        reviewDTO.setReviewDescription(reviewBookRequest.getReviewDescription());

        Review validateReview = this.reviewRepository.findByUserEmailAndBookId(userEmail, reviewDTO.getBookId());

        // Book has review 
        if(validateReview != null) {
            throw new Exception("Review already created");
        }

        Review newReview = this.modelMapper.map(reviewDTO, Review.class);
        System.out.println(newReview.toString());

        // If review has value(String)
        if (reviewDTO.getReviewDescription().isPresent()) {
            newReview.setReviewDescription(
                    reviewDTO.getReviewDescription().map(Objects::toString)
                            .orElse(null)
            );
        }

        this.reviewRepository.save(newReview);
    }

    @Override
    public boolean isReviewByUser(String userEmail, long bookId) {
        Review review = this.reviewRepository.findByUserEmailAndBookId(userEmail, bookId);

        return review != null;
    }

}
