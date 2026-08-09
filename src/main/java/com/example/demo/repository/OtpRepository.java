package com.example.demo.repository;

import com.example.demo.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Otp findByEmail(String email);

    Otp findByEmailAndOtp(String email, String otp);

}