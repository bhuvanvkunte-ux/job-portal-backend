package com.example.demo.repository;

import com.example.demo.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavedJobRepository
        extends JpaRepository<SavedJob, Long> {

    List<SavedJob> findByCandidateEmail(String candidateEmail);

    long countByCandidateEmail(String email);

    boolean existsByCandidateEmailAndJobId(String candidateEmail, Long jobId);
}