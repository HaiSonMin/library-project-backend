package com.haison.libraryapplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

@Entity
@Table(name = "books")
@Data // Using Lombok library
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "description")
    private String description;
    @Column(name = "copies")
    private int copies;
    @Column(name = "copies_available")
    private int copies_available;
    @Column(name = "category")
    private String category;
    @Column(name = "img")
    private String img;

}
