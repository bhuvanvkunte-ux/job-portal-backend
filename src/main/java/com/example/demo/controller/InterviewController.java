package com.example.demo.controller;

import com.example.demo.entity.Interview;
import com.example.demo.service.InterviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(
        name = "Interview APIs",
        description = "Interview Scheduling Operations"
)
@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Operation(summary = "Schedule Interview")
    @PostMapping
    public ResponseEntity<?> scheduleInterview(
            @RequestBody Interview interview
    ) {
        Interview savedInterview =
                interviewService.scheduleInterview(interview);

        return ResponseEntity.ok(savedInterview);
    }

    @Operation(summary = "Get All Interviews")
    @GetMapping
    public ResponseEntity<?> getAllInterviews() {

        List<Interview> interviews =
                interviewService.getAllInterviews();

        return ResponseEntity.ok(interviews);
    }

    @Operation(summary = "Get Candidate Interviews")
    @GetMapping("/candidate/{email}")
    public ResponseEntity<?> getCandidateInterviews(
            @PathVariable String email
    ) {
        List<Interview> interviews =
                interviewService.getInterviewsByCandidateEmail(email);

        return ResponseEntity.ok(interviews);
    }

    @Operation(summary = "Update Interview Status")
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateInterviewStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        String status = request.get("status");

        if (
                status == null ||
                        status.isEmpty()
        ) {
            return ResponseEntity.badRequest().body("Status is required");
        }

        Interview updatedInterview =
                interviewService.updateInterviewStatus(id, status);

        if (updatedInterview == null) {
            return ResponseEntity.status(404).body("Interview not found");
        }

        return ResponseEntity.ok(updatedInterview);
    }
}