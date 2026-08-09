package com.example.demo.service;

import com.example.demo.entity.Job;
import com.example.demo.entity.RecruiterProfile;
import com.example.demo.entity.User;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.RecruiterProfileRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public Job saveJob(Job job) {

        if (job.getStatus() == null || job.getStatus().isEmpty()) {
            job.setStatus("ACTIVE");
        }

        RecruiterProfile profile =
                recruiterProfileRepository.findByRecruiterEmail(
                        job.getRecruiterEmail()
                );

        if (profile != null) {
            job.setCompanyName(profile.getCompanyName());
            job.setCompanyWebsite(profile.getCompanyWebsite());
            job.setCompanyDescription(profile.getCompanyDescription());
            job.setCompanyLocation(profile.getCompanyLocation());
        }

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {

        String today = java.time.LocalDate.now().toString();

        return jobRepository.findByExpiryDateGreaterThanEqual(today);
    }

    public Job updateJob(Long id, Job updatedJob) {

        Job existingJob = jobRepository.findById(id).orElse(null);

        if (existingJob != null) {

            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setCompanyName(updatedJob.getCompanyName());
            existingJob.setLocation(updatedJob.getLocation());
            existingJob.setSalary(updatedJob.getSalary());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setSkills(updatedJob.getSkills());
            existingJob.setExpiryDate(updatedJob.getExpiryDate());

            return jobRepository.save(existingJob);
        }

        return null;
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public List<Job> getJobsByLocation(String location) {
        return jobRepository.findByLocation(location);
    }

    public List<Job> getJobsByCompany(String companyName) {
        return jobRepository.findByCompanyName(companyName);
    }

    public List<Job> searchBySkill(String skill) {
        return jobRepository.findBySkillsContaining(skill);
    }

    public List<Job> searchByLocation(String location) {
        return jobRepository.findByLocationContaining(location);
    }

    public List<Job> getActiveJobs() {
        return jobRepository.findByStatus("ACTIVE");
    }

    public Page<Job> getJobsWithPagination(int page, int size) {

        return jobRepository.findAll(
                PageRequest.of(page, size)
        );
    }

    public List<Job> getJobsSortedBySalary() {

        return jobRepository.findAll(
                Sort.by(
                        Sort.Direction.DESC,
                        "salary"
                )
        );
    }

    public List<Job> getJobsByRecruiter(String recruiterEmail) {

        return jobRepository.findByRecruiterEmail(recruiterEmail);
    }

    public Job getJobById(Long id) {

        return jobRepository.findById(id).orElse(null);
    }

    public Job closeJob(Long id) {

        Job job = jobRepository.findById(id).orElse(null);

        if (job == null) {
            return null;
        }

        job.setStatus("CLOSED");

        return jobRepository.save(job);
    }

    public List<Job> recommendJobs(String email) {

        User user = userRepository.findByEmail(email);

        if (
                user == null ||
                        user.getSkills() == null ||
                        user.getSkills().isEmpty()
        ) {
            return List.of();
        }

        String[] skills = user.getSkills().split(",");

        List<Job> recommendedJobs = new ArrayList<>();

        for (String skill : skills) {

            String trimmedSkill = skill.trim();

            if (!trimmedSkill.isEmpty()) {

                List<Job> matchingJobs =
                        jobRepository.findByStatusAndSkillsContainingIgnoreCase(
                                "ACTIVE",
                                trimmedSkill
                        );

                for (Job job : matchingJobs) {

                    if (!recommendedJobs.contains(job)) {
                        recommendedJobs.add(job);
                    }
                }
            }
        }

        return recommendedJobs;
    }
}