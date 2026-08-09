package com.example.demo.controller;

import com.example.demo.dto.AdminDashboardResponse;
import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Job;
import java.util.List;
import com.example.demo.entity.Application;


@Tag(
        name = "Admin APIs",
        description = "Admin Dashboard and Management APIs"
)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Get Admin Dashboard")
    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {

        return adminService.getDashboard();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return adminService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        System.out.println("DELETE API HIT");

        return adminService.deleteUser(id);
    }

    @GetMapping("/recruiters")
    public List<User> getRecruiters() {

        return adminService.getAllRecruiters();
    }

    @GetMapping("/candidates")
    public List<User> getCandidates() {

        return adminService.getAllCandidates();
    }

    @GetMapping("/jobs")
    public List<Job> getAllJobs() {

        return adminService.getAllJobs();
    }

    @DeleteMapping("/jobs/{id}")
    public String deleteJob(
            @PathVariable Long id) {

        return adminService.deleteJob(id);
    }

    @GetMapping("/applications")
    public List<Application> getAllApplications() {

        return adminService.getAllApplications();
    }
}