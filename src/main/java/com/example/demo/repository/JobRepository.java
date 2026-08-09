package com.example.demo.repository;

import com.example.demo.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByLocation(String location);

    List<Job> findByCompanyName(String companyName);

    List<Job> findBySkillsContaining(String skills);

    List<Job> findByLocationContaining(String location);

    List<Job> findByExpiryDateGreaterThanEqual(String today);

    List<Job> findByRecruiterEmail(String recruiterEmail);

    List<Job> findByStatus(String status);

    List<Job> findByStatusAndSkillsContainingIgnoreCase(String status, String skill);
}