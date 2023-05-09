package com.haison.libraryapplication.requestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

// ------------------ Request is sent from client to server ------------------
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewBookRequest {
    private long bookId;
    private double rating;
    private Optional<String> reviewDescription;
}
