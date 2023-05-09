package com.haison.libraryapplication.service;

import com.haison.libraryapplication.dto.BookDTO;
import com.haison.libraryapplication.responsemodels.ShelfCurrentLoansResponse;

import java.util.List;

public interface BookService {
    // Create || Update
    BookDTO createBook(BookDTO theBook);
    BookDTO checkoutBook(String userEmail, long bookId) throws Exception;
    BookDTO renewLoanBook(String userEmail, long bookId) throws Exception;
    Boolean isCheckoutBookByUser(String useEmail, long bookId);
    int currentBooksCheckout(String userEmail);
    List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception;
    void returnBook(String userEmail, long bookId) throws Exception;
    List<BookDTO> getAllBook();
    BookDTO getBookById(long id);
    List<BookDTO> getBooksByTitle(String title);
    List<BookDTO>  getBooksByCategory(String category);
    BookDTO updateBookById(BookDTO bookDTO, long id);
    void deleteBookById(long id);
    void deleteAll();
}
