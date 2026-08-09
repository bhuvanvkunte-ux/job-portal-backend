package com.example.demo.controller;

import com.example.demo.dto.DashboardResponse;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.InterviewRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.dto.CandidateDashboardResponse;

@Tag(
        name = "Dashboard APIs",
        description = "Recruiter Dashboard Statistics"
)
@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Operation(summary = "Get Recruiter Dashboard Statistics")
    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {

        return dashboardService.getDashboard();
    }

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/analytics")
    public Map<String,Object> getAnalytics() {

        Map<String,Object> analytics =
                new HashMap<>();

        analytics.put("totalJobs",
                jobRepository.count());

        analytics.put("totalApplications",
                applicationRepository.count());

        analytics.put("totalInterviews",
                interviewRepository.count());

        analytics.put("totalCandidates",
                userRepository.count());

        return analytics;
    }

    @Operation(summary = "Get Candidate Dashboard")
    @GetMapping("/dashboard/candidate")
    public CandidateDashboardResponse getCandidateDashboard(
            @RequestParam String email) {

        return dashboardService.getCandidateDashboard(email);
    }

}