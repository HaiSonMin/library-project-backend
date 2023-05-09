package com.haison.libraryapplication.repository;

import com.haison.libraryapplication.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


// Include Entity and Primary Key
@CrossOrigin(origins = "https://localhost:3000" )// Allow localhost:3000 connect
public interface BookRepository extends JpaRepository<Book,Long> {

    // Search with title and Pageable
    Page<Book> findByTitleContaining(@Param("title") String title,Pageable pageable);

    // Search with category and Pageable
    Page<Book> findByCategoryContaining(@Param("category") String category,Pageable pageable);

    @Query(value = """
            SELECT * FROM books b
            WHERE b.id IN :book_ids
            """,nativeQuery = true)
    List<Book> findBookByBookIds(@Param("book_ids") List<Long> listBookId);

}
