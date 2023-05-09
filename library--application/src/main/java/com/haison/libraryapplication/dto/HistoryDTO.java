package com.haison.libraryapplication.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    private long id;
    private String userEmail;
    private LocalDateTime checkoutDate;
    private LocalDateTime returnDate;
    private String title;
    private String author;
    private String description;
    private String img;
}
