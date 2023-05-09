package com.haison.libraryapplication.responsemodels;


import com.haison.libraryapplication.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ------------------ Response is receive from server to client ------------------
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShelfCurrentLoansResponse {
    private Book book;
    private int daysLeft;
}




