package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.dto.StatusUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;

@Tag(
        name = "Application APIs",
        description = "Job Application Operations"
)
@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Operation(summary = "Apply For Job")
    @PostMapping("/apply")
    public ResponseEntity<?> applyJob(@RequestBody Application application) {
        try {
            Application savedApplication = applicationService.applyJob(application);
            return ResponseEntity.ok(savedApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get All Applications")
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @Operation(summary = "Update Application Status")
    @PutMapping("/{id}/status")
    public Application updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request
    )
    {

        return applicationService.updateStatus(
                id,
                request.getStatus()
        );
    }

    @Operation(summary = "Get Candidate Applications")
    @GetMapping("/candidate/{email}")
    public List<Application> getApplicationsByEmail(
            @PathVariable String email) {

        return applicationService.getApplicationsByEmail(email);
    }

    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJobId(
            @PathVariable Long jobId) {

        return applicationService
                .getApplicationsByJobId(jobId);
    }
}