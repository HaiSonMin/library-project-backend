package com.haison.libraryapplication.controller;

import com.haison.libraryapplication.requestModels.ReviewBookRequest;
import com.haison.libraryapplication.service.ReviewService;
import com.haison.libraryapplication.utils.ExtractJWT;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("command/reviews/secure")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestHeader("Authorization") String token,
                                               @RequestBody ReviewBookRequest reviewBookRequest) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");


        this.reviewService.createReview(userEmail, reviewBookRequest);

        return new ResponseEntity<>("Create review successfully", HttpStatus.CREATED);
    }

    @GetMapping("is-review/by-user")
    public ResponseEntity<Boolean> isReviewByUser(@RequestHeader("Authorization") String token,
                                                  @RequestParam("book_id") long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");

        boolean isReviewBook = this.reviewService.isReviewByUser(userEmail, bookId);

        return new ResponseEntity<>(isReviewBook, HttpStatus.OK);
    }

//    @GetMapping("secure/current-reviews/total")
//    public ResponseEntity<Boolean> currentReviews(@RequestHeader("Authorization") String token,
//                                                  @RequestParam("id") long bookId) {
//        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
//
//        boolean isReviewBook = this.reviewService.isReviewByUser(userEmail, bookId);
//
//        return new ResponseEntity<>(isReviewBook, HttpStatus.OK);
//    }
}
