package com.haison.libraryapplication.repository;

import com.haison.libraryapplication.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("https://localhost:3000")
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByUserEmail(@Param("user_email") String userEmail, Pageable pageable);

    Page<Message> findByClosed(@Param("closed") boolean closed, Pageable pageable);
}
