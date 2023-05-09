package com.haison.libraryapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "title")
    private String title;
    @Column(name = "question")
    private String question;
    @Column(name = "admin_email")
    private String adminEmail;
    @Column(name = "response")
    private String response;
    @Column(name = "closed")
    private boolean closed;

}
