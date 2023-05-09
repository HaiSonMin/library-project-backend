package com.haison.libraryapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;
    @Column(name = "rating")
    private double rating;
    @Column(name = "book_id")
    private long bookId;
    @Column(name = "review_description")
    private String reviewDescription;

}
