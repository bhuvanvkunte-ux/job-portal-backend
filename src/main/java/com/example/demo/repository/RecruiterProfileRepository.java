package com.example.demo.repository;

import com.example.demo.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterProfileRepository
        extends JpaRepository<RecruiterProfile, Long> {

    RecruiterProfile findByRecruiterEmail(String recruiterEmail);
}