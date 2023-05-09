package com.haison.libraryapplication.repository;

import com.haison.libraryapplication.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("https://localhost:3000")
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findByUserEmailAndBookId(String userEmail, long bookId);

    List<Checkout> findByUserEmail(String userEmail);

    // @Modifying (SELECT, UPDATE, DELETE, INSERT)
    @Modifying
    @Query(value = """
            DELETE FROM checkouts c
            WHERE c.book_id = :book_id
            """,nativeQuery = true)
    void deleteAllByBookId(@Param("book_id") long bookId);
}
