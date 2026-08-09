package com.example.demo.repository;

import com.example.demo.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByEmail(String email);

    List<Application> findByJobId(Long jobId);

    long countByEmail(String email);

    boolean existsByEmailAndJobId(String email, Long jobId);
}