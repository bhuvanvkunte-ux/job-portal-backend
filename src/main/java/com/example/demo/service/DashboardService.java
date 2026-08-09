package com.example.demo.service;

import com.example.demo.dto.DashboardResponse;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CandidateDashboardResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.InterviewRepository;
import com.example.demo.repository.SavedJobRepository;
import com.example.demo.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public DashboardResponse getDashboard() {

        long totalJobs = jobRepository.count();

        long totalApplications =
                applicationRepository.count();

        return new DashboardResponse(
                totalJobs,
                totalApplications
        );
    }

    @Autowired
    private SavedJobRepository savedJobRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;

    public CandidateDashboardResponse getCandidateDashboard(String email) {

        CandidateDashboardResponse response =
                new CandidateDashboardResponse();

        response.setAppliedJobs(
                applicationRepository.countByEmail(email));

        response.setSavedJobs(
                savedJobRepository.countByCandidateEmail(email));

        response.setScheduledInterviews(
                interviewRepository.countByCandidateEmail(email));

        User user =
                userRepository.findByEmail(email);

        response.setResumeUploaded(
                user.getResumePath() != null &&
                        !user.getResumePath().isEmpty());

        return response;
    }
}