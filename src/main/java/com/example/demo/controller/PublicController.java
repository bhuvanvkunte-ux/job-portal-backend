package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.entity.Interview;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.InterviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @GetMapping("/hero-data")
    public Map<String, Object> getHeroData() {

        List<Application> applications = applicationRepository.findAll();
        List<Interview> interviews = interviewRepository.findAll();

        Application latestSelectedApplication = applications.stream()
                .filter(app -> app.getStatus() != null)
                .filter(app -> app.getStatus().equalsIgnoreCase("SELECTED"))
                .max(Comparator.comparing(Application::getId))
                .orElse(null);

        Interview latestScheduledInterview = interviews.stream()
                .filter(interview -> interview.getStatus() != null)
                .filter(interview -> interview.getStatus().equalsIgnoreCase("SCHEDULED"))
                .max(Comparator.comparing(Interview::getId))
                .orElse(null);

        Map<String, Object> response = new HashMap<>();

        response.put("latestSelectedApplication", latestSelectedApplication);
        response.put("latestScheduledInterview", latestScheduledInterview);
        response.put("systemStatus", "JWT Authentication Active");

        return response;
    }
}