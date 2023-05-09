package com.haison.libraryapplication.repository;

import com.haison.libraryapplication.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("https://localhost:3000")
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByBookId(@Param("book_id") Long bookId, Pageable pageable);

    Review findByUserEmailAndBookId(String userEmail, long bookId);

    @Modifying
    @Query(value = """
            DELETE FROM reviews r
            WHERE r.book_id = :book_id"""
            ,nativeQuery = true)
    void deleteAllByBookId(@Param("book_id") long bookId);
}
