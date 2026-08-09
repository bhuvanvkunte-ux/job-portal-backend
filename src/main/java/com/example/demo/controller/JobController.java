package com.example.demo.controller;

import com.example.demo.entity.Job;
import com.example.demo.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Job APIs", description = "Operations related to jobs")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public Job saveJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PutMapping("/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @RequestBody Job job
    ) {
        return jobService.updateJob(id, job);
    }

    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);

        return "Job Deleted Successfully";
    }

    @GetMapping("/location/{location}")
    public List<Job> getJobsByLocation(
            @PathVariable String location
    ) {
        return jobService.getJobsByLocation(location);
    }

    @GetMapping("/company/{companyName}")
    public List<Job> getJobsByCompany(
            @PathVariable String companyName
    ) {
        return jobService.getJobsByCompany(companyName);
    }

    @GetMapping("/search/skill")
    public List<Job> searchBySkill(
            @RequestParam String skill
    ) {
        return jobService.searchBySkill(skill);
    }

    @GetMapping("/search/location")
    public List<Job> searchByLocation(
            @RequestParam String location
    ) {
        return jobService.searchByLocation(location);
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> recommendJobs(
            @RequestParam String email
    ) {
        List<Job> jobs = jobService.recommendJobs(email);

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/active")
    public List<Job> getActiveJobs() {
        return jobService.getActiveJobs();
    }

    @GetMapping("/page")
    public Page<Job> getJobsPage(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return jobService.getJobsWithPagination(page, size);
    }

    @GetMapping("/sort/salary")
    public List<Job> sortJobsBySalary() {
        return jobService.getJobsSortedBySalary();
    }

    @GetMapping("/my-jobs")
    public List<Job> getMyJobs(
            @RequestParam String email
    ) {
        return jobService.getJobsByRecruiter(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(
            @PathVariable Long id
    ) {
        Job job = jobService.getJobById(id);

        if (job == null) {
            return ResponseEntity.status(404).body("Job not found");
        }

        return ResponseEntity.ok(job);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<?> closeJob(
            @PathVariable Long id
    ) {
        Job closedJob = jobService.closeJob(id);

        if (closedJob == null) {
            return ResponseEntity.status(404).body("Job not found");
        }

        return ResponseEntity.ok("Job closed successfully");
    }
}