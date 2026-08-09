package com.example.demo.service;

import com.example.demo.dto.AdminDashboardResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.entity.Job;
import com.example.demo.entity.Application;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private SavedJobRepository savedJobRepository;

    public AdminDashboardResponse getDashboard() {

        AdminDashboardResponse dashboard =
                new AdminDashboardResponse();

        dashboard.setTotalUsers(
                userRepository.count());

        dashboard.setTotalRecruiters(
                userRepository.findByRole("RECRUITER").size());

        dashboard.setTotalCandidates(
                userRepository.findByRole("CANDIDATE").size());

        dashboard.setTotalJobs(
                jobRepository.count());

        dashboard.setTotalApplications(
                applicationRepository.count());

        dashboard.setTotalInterviews(
                interviewRepository.count());

        dashboard.setTotalSavedJobs(
                savedJobRepository.count());

        return dashboard;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public String deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            return "User Not Found";
        }

        userRepository.deleteById(id);

        return "User Deleted Successfully";
    }

    public List<User> getAllRecruiters() {

        return userRepository.findByRole("RECRUITER");
    }

    public List<User> getAllCandidates() {

        return userRepository.findByRole("CANDIDATE");
    }

    public List<Job> getAllJobs() {

        return jobRepository.findAll();
    }

    public String deleteJob(Long id) {

        jobRepository.deleteById(id);

        return "Job Deleted Successfully";
    }

    public List<Application> getAllApplications() {

        return applicationRepository.findAll();
    }
}