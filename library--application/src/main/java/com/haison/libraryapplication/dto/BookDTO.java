package com.haison.libraryapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private long id;
    private String title;
    private String author;
    private String description;
    private int copies;
    private String category;
    private String img;


}
