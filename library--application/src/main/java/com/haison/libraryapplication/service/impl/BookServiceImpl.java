package com.haison.libraryapplication.service.impl;

import com.haison.libraryapplication.entity.Payment;
import com.haison.libraryapplication.repository.*;
import com.haison.libraryapplication.dto.BookDTO;
import com.haison.libraryapplication.entity.Book;
import com.haison.libraryapplication.entity.Checkout;
import com.haison.libraryapplication.entity.History;
import com.haison.libraryapplication.error.BookExceptionNotFound;
import com.haison.libraryapplication.responsemodels.ShelfCurrentLoansResponse;
import com.haison.libraryapplication.service.BookService;
import com.haison.libraryapplication.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "bookServiceImpl")
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;
    private final HistoryRepository historyRepository; // Create when return book
    private final ReviewRepository reviewRepository;
    private final PaymentRepository paymentRepository; // Create when payment book

    @Override
    public BookDTO createBook(BookDTO bookDTO) {

        Book book = this.modelMapper.map(bookDTO,Book.class);
        book.setCopies_available(bookDTO.getCopies());

        Book newBook = this.bookRepository.save(book);

        return this.modelMapper.map(newBook,BookDTO.class);

    }



    @Override
    public int currentBooksCheckout(String userEmail) {
        List<Checkout> listBooksCheckout = this.checkoutRepository.findByUserEmail(userEmail);

        return listBooksCheckout.size();
    }

    @Override
    public BookDTO checkoutBook(String userEmail, long bookId) throws Exception {
        // Get book
        Optional<Book> book = this.bookRepository.findById(bookId);
        // Get checkout
        Checkout validateCheckout = this.checkoutRepository.findByUserEmailAndBookId(userEmail,bookId);

        // Throw Exception if
        // 1. Book doesn't exist
        // 2. Book has checkout (checkout has existed)
        // 3. Copies <= 0 => Can't loan
        if(book.isEmpty() || validateCheckout != null || book.get().getCopies_available() <= 0 ) {
            throw new BookExceptionNotFound("Book doesn't exists or already checked out by user");
        }

        // User need payment books return late until checkout new book
        List<Checkout> checkoutList = this.checkoutRepository.findByUserEmail(userEmail);
        boolean bookNeedReturned = false;
        for (Checkout checkout:checkoutList) {
            LocalDateTime checkoutDay = checkout.getCheckoutDate();
            LocalDateTime returnDay = checkout.getReturnDate();

            int dayReturnBook = returnDay.getDayOfYear() - checkoutDay.getDayOfYear();

            if(dayReturnBook < 0) {
                bookNeedReturned = true;
                break;
            }

        }

        Payment userPayment = this.paymentRepository.findByUserEmail(userEmail);

        // If user checkout when too deadline, 1 in the number of the failed
        if((userPayment != null && userPayment.getAmount() > 0) || (userPayment != null && bookNeedReturned))
            throw new Exception("Outstanding fees");

        // When user checkout, payment have created with information reader
        // If user checkout when successfully paid or all books are not due for payment
        if(userPayment == null) {
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            this.paymentRepository.save(payment);
        }

        book.get().setCopies_available(book.get().getCopies_available()-1);
        this.bookRepository.save(book.get());

        // Create Checkout by user when click checkout book
        Checkout checkout = new Checkout();
        checkout.setUserEmail(userEmail);
        checkout.setCheckoutDate(LocalDateTime.now());
        checkout.setReturnDate(LocalDateTime.now().plusDays(7));
        checkout.setBookId(book.get().getId());

        this.checkoutRepository.save(checkout);

        return this.modelMapper.map(book.get(),BookDTO.class);
    }

    @Override
    public Boolean isCheckoutBookByUser(String useEmail, long bookId) {
        Checkout validateCheckout = this.checkoutRepository.findByUserEmailAndBookId(useEmail,bookId);

        return validateCheckout != null;
    }

    @Override
    public BookDTO renewLoanBook(String userEmail, long bookId) throws Exception {
        Optional<Book> book = this.bookRepository.findById(bookId);

        Checkout validateCheckout = this.checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(book.isEmpty() && validateCheckout == null) {
            throw new Exception("Book doesn't exists or checkout haven't not yet!!!");
        }

        validateCheckout.setReturnDate(validateCheckout.getReturnDate().plusDays(7));

        this.checkoutRepository.save(validateCheckout);

        return this.modelMapper.map(book,BookDTO.class);
    }

    @Override
    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail)  {
        // Return books, dateLoans
        List<Long> listBookId = new ArrayList<>();
        List<ShelfCurrentLoansResponse> listBookCurrentLoans = new ArrayList<>();
        List<Checkout> checkoutsBookByEmail = this.checkoutRepository.findByUserEmail(userEmail);

        for (Checkout checkout: checkoutsBookByEmail) {
            listBookId.add(checkout.getBookId());
        }

        List<Book> listBooks = this.bookRepository.findBookByBookIds(listBookId);

        // Format date
//        SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
//        int i=0;
        for(Book book:listBooks) {
            // ------------ Way 1 ------------:
            // Check Checkout by ID have exists
            Optional<Checkout> newCheckout = checkoutsBookByEmail.stream().filter(
                    checkout -> checkout.getBookId() == book.getId()
            ).findFirst();

            if (newCheckout.isPresent()) {

//                LocalDateTime nowDay = LocalDateTime.now();
                LocalDateTime checkoutDay = newCheckout.get().getCheckoutDate();
                LocalDateTime returnDay = newCheckout.get().getReturnDate();

//                int dayReturnBook = returnDay.getDayOfYear() - nowDay.getDayOfYear();
                int dayReturnBook = returnDay.getDayOfYear() - checkoutDay.getDayOfYear();

                listBookCurrentLoans.add(new ShelfCurrentLoansResponse(book,dayReturnBook));
            } else throw new BookExceptionNotFound("Book don't exists");

                // Way 2:
//                LocalDateTime dayReturn = checkoutsBookByEmail.get(i).getReturnDate();
//                LocalDateTime dayNow = LocalDateTime.now();
//                int dayReturnBook = dayReturn.getDayOfYear() - dayNow.getDayOfYear();
//                listBookCurrentLoans.add(new ShelfCurrentLoansResponse(book,dayReturnBook));
        }
        return  listBookCurrentLoans;
    }

    @Override
    public void returnBook(String userEmail, long bookId) throws Exception {
        Optional<Book> book = this.bookRepository.findById(bookId);

        Checkout validateCheckout = this.checkoutRepository.findByUserEmailAndBookId(userEmail,bookId);
//        boolean isExistBookInHistory = this.historyRepository.existsByUserEmailAndBookId(userEmail,bookId);

        if(validateCheckout == null && book.isEmpty()) {
            throw new Exception("Book dose not exists or haven't checked out yet");
        }

        // Check number of date before return book
        assert validateCheckout != null;
        LocalDateTime returnDay = validateCheckout.getReturnDate();
        LocalDateTime checkoutDay = validateCheckout.getCheckoutDate();

        int dayReturnBook = returnDay.getDayOfYear() - checkoutDay.getDayOfYear();
        if(dayReturnBook < 0) {
            Payment payment = this.paymentRepository.findByUserEmail(userEmail);

            payment.setAmount(dayReturnBook * (-1));

            this.paymentRepository.save(payment);
        }

        // Complete return the book for library and remove to shelfBook and increment Copies_available+1
        book.get().setCopies_available(book.get().getCopies_available()+1);
        this.bookRepository.save(book.get());
        this.checkoutRepository.delete(validateCheckout);

            // Save to history when return books
        History history = new History();
        history.setUserEmail(userEmail);
        history.setAuthor(book.get().getAuthor());
        history.setTitle(book.get().getTitle());
        history.setDescription(book.get().getDescription());
        history.setCheckoutDate(validateCheckout.getCheckoutDate());
        history.setReturnDate(LocalDateTime.now());
        history.setImg(book.get().getImg());
        this.historyRepository.save(history);
    }

    @Override
    public List<BookDTO> getAllBook() {
        List<Book> listBook = this.bookRepository.findAll();

        return listBook.stream().map(book->this.modelMapper.map(book,BookDTO.class))
                       .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(long id) {

        Book book = this.bookRepository.findById(id).orElseThrow(
                ()-> new BookExceptionNotFound("Dose not book exist")
        );
        return this.modelMapper.map(book,BookDTO.class);
    }


    @Override
    public List<BookDTO> getBooksByTitle(String title) {
        Page<Book> listBook = this.bookRepository.findByTitleContaining(title, Pageable.ofSize(5));

        return listBook.stream().map(book->this.modelMapper.map(book,BookDTO.class))
                       .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByCategory(String category) {


        Page<Book> listBook = this.bookRepository.findByTitleContaining(category, Pageable.ofSize(5));

        return listBook.stream().map(book->this.modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBookById(BookDTO bookDTO, long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(
                ()-> new BookExceptionNotFound("Update failed cause the book dose not exist")
        );

        int copies_available = book.getCopies_available();
        int old_copies = book.getCopies();

        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setCategory(bookDTO.getCategory());
        book.setCopies(bookDTO.getCopies());
        book.setCopies_available(bookDTO.getCopies()-old_copies + copies_available);
        book.setImg(bookDTO.getImg());

        return this.modelMapper.map(this.bookRepository.save(book),BookDTO.class);
    }

    @Override
    public void deleteBookById(long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(
                ()->new BookExceptionNotFound("Delete failed cause the book dose not exist")
        );

        this.bookRepository.delete(book);
        this.checkoutRepository.deleteAllByBookId(book.getId());
        this.reviewRepository.deleteAllByBookId(book.getId());
    }

    @Override
    public void deleteAll() {
        this.bookRepository.deleteAll();
    }
}
