package com.haison.libraryapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "checkouts")
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "checkout_date")
    private LocalDateTime checkoutDate;
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    @Column(name = "book_id")
    private long bookId;


}
