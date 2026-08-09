package com.example.demo.controller;

import com.example.demo.entity.SavedJob;
import com.example.demo.service.SavedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;

@Tag(
        name = "Saved Job APIs",
        description = "Operations related to saving and removing jobs"
)
@RestController
@RequestMapping("/saved-jobs")
public class SavedJobController {

    @Autowired
    private SavedJobService savedJobService;

    @Operation(summary = "Save Job")
    @PostMapping
    public ResponseEntity<?> saveJob(@RequestBody SavedJob savedJob) {
        try {
            SavedJob saved = savedJobService.saveJob(savedJob);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get Saved Jobs By Candidate Email")
    @GetMapping("/{email}")
    public List<SavedJob> getSavedJobs(
            @PathVariable String email) {

        return savedJobService.getSavedJobs(email);
    }

    @Operation(summary = "Remove Saved Job")
    @DeleteMapping("/{id}")
    public String removeSavedJob(
            @PathVariable Long id) {

        savedJobService.removeSavedJob(id);

        return "Saved Job Removed";
    }
}