package com.haison.libraryapplication.repository;

import com.haison.libraryapplication.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("https://localhost:3000")
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUserEmail(@Param("user_email") String userEmail);
}
