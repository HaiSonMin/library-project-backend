package com.haison.libraryapplication.controller;

import com.haison.libraryapplication.dto.BookDTO;
import com.haison.libraryapplication.responsemodels.ShelfCurrentLoansResponse;
import com.haison.libraryapplication.service.BookService;
import com.haison.libraryapplication.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("command/books/secure")
//@AllArgsConstructor
public class BookController {

    private final  BookService bookService;
    @Autowired
    public BookController(@Qualifier("bookServiceImpl") BookService bookService) {
        this.bookService = bookService;

    }

    // -------------- Create --------------
    @PostMapping("create-book")
    public ResponseEntity<BookDTO> createBook(@RequestHeader("Authorization") String token
                                             ,@RequestBody BookDTO bookDTO) {
        BookDTO newBook = this.bookService.createBook(bookDTO);

        return new ResponseEntity<>(newBook,HttpStatus.CREATED);
    }

    // -------------- Read --------------
//    @GetMapping()
//    public ResponseEntity<List<BookDTO>> getAllBook() {
//        List<BookDTO> listBook = this.bookService.getAllBook();
//
//        return new ResponseEntity<>(listBook,HttpStatus.OK);
//    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(value = "id") long bookId){
        BookDTO book = this.bookService.getBookById(bookId);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @GetMapping("current-loans")
    public ResponseEntity<List<ShelfCurrentLoansResponse>> getBookCurrentLoansByEmail(
                                                @RequestHeader("Authorization") String token) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");

        List<ShelfCurrentLoansResponse> currentLoans = this.bookService.currentLoans(userEmail);

        return new ResponseEntity<>(currentLoans,HttpStatus.OK);

    }

    @GetMapping("query-title")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@RequestParam("title") String title){
        List<BookDTO> listBook = this.bookService.getBooksByTitle(title);
        return new ResponseEntity<>(listBook,HttpStatus.OK);
    }

    @GetMapping("query-category")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@RequestParam("category") String category){
        List<BookDTO> listBook = this.bookService.getBooksByCategory(category);

        return new ResponseEntity<>(listBook,HttpStatus.OK);
    }

    @GetMapping("is-checkout/by-user")
    public ResponseEntity<Boolean> checkoutBookByUser(@RequestHeader(value = "Authorization") String token,
                                                      @RequestParam("book_id") long bookId) {
//        String userEmail = "haison@gmail.com";
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        boolean isCheckoutByUser =  this.bookService.isCheckoutBookByUser(userEmail, bookId);
        return new ResponseEntity<>(isCheckoutByUser, HttpStatus.OK);
    }

    @GetMapping("current-loans/count")
    public ResponseEntity<Integer> currentBooksLoan(@RequestHeader(value = "Authorization") String token) {
//        String userEmail = "haison@gmail.com";
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        int currentBooksLoan =  this.bookService.currentBooksCheckout(userEmail);
        return new ResponseEntity<>(currentBooksLoan, HttpStatus.OK);
    }

    // -------------- Update --------------
    @PutMapping("return-book")
    public ResponseEntity<String> returnBook(@RequestHeader("Authorization") String token,
                                             @RequestParam("book_id") long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        this.bookService.returnBook(userEmail, bookId);
        return ResponseEntity.ok("Return successfully");
    }
    @PutMapping("checkout")
    public ResponseEntity<BookDTO> checkoutBook(@RequestHeader(value = "Authorization") String token,
                                                @RequestParam("book_id") long bookId) throws Exception {
//        String userEmail = "haison@gmail.com";
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        BookDTO bookCheckout = this.bookService.checkoutBook(userEmail,bookId);
        return new ResponseEntity<>(bookCheckout, HttpStatus.OK);
    }

    @PutMapping("renew-loan")
    public ResponseEntity<BookDTO> renewLoanBook(@RequestHeader("Authorization") String token,
                                                 @RequestParam("book_id") long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        BookDTO bookDTO = this.bookService.renewLoanBook(userEmail, bookId);

        return new ResponseEntity<>(bookDTO,HttpStatus.OK);
    }

    @PutMapping("update-book/{id}")
    public ResponseEntity<BookDTO> updateBookById(@RequestBody BookDTO bookDTO,@PathVariable("id") long bookId) {
        BookDTO newBookUpdate = this.bookService.updateBookById(bookDTO,bookId);

        return new ResponseEntity<>(newBookUpdate,HttpStatus.OK);
    }


    // -------------- Delete --------------
    @DeleteMapping("delete-book/{id}")
    public ResponseEntity<String> deleteBookById(@RequestHeader("Authorization") String token,
                                                 @PathVariable(value = "id") long bookId){
//         String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
         this.bookService.deleteBookById(bookId);
         return ResponseEntity.ok("Delete Successfully");
    }

//    @DeleteMapping()
//    public ResponseEntity<String> deleteAllBook(){
//        this.bookService.deleteAll();
//        return ResponseEntity.ok("Delete All Successfully");
//    }

}
