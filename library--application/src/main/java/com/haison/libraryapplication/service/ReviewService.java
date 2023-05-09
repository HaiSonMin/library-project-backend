package com.haison.libraryapplication.service;

import com.haison.libraryapplication.dto.ReviewDTO;
import com.haison.libraryapplication.requestModels.ReviewBookRequest;

public interface ReviewService {

    public void createReview(String userEmail, ReviewBookRequest reviewBookRequest) throws Exception;
    public boolean isReviewByUser(String userEmail, long bookId);
//    public int currentReviewsOfTheBook(long bookId);
}
