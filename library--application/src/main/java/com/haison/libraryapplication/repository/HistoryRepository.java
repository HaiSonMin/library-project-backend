package com.haison.libraryapplication.repository;

import com.haison.libraryapplication.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("https://localhost:3000")
public interface HistoryRepository extends JpaRepository<History,Long> {

//    Page<History> findBooksByUserEmail(@Param("user_email") String userEmail, Pageable pageable);
    Page<History> findByUserEmail(@Param("user_email") String userEmail, Pageable pageable);
    boolean findByUserEmailAndTitle(String userEmail, String title);
//    public boolean existsByUserEmailAndBookId(String userEmail,long bookId);
}
