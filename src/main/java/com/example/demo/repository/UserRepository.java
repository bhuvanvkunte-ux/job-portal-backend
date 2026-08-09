package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findBySkillsContaining(String skills);

    long countByRole(String role);

    List<User> findByRole(String role);
}
