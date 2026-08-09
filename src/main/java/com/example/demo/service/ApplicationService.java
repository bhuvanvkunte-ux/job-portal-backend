package com.example.demo.service;

import com.example.demo.entity.Application;
import com.example.demo.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EmailService emailService;

    public Application applyJob(Application application) {

        boolean alreadyApplied = applicationRepository.existsByEmailAndJobId(
                application.getEmail(),
                application.getJobId()
        );

        if (alreadyApplied) {
            throw new RuntimeException("Already applied for this job");
        }

        application.setStatus("PENDING");

        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application updateStatus(
            Long id,
            String status)
    {
        Application application =
                applicationRepository.findById(id)
                        .orElseThrow();

        application.setStatus(status);

        Application updatedApplication =
                applicationRepository.save(application);

        if(status.equalsIgnoreCase("SHORTLISTED")) {

            emailService.sendEmail(
                    application.getEmail(),
                    "Application Status Update - Job Portal",
                    "Dear " + application.getCandidateName() + ",\n\n" +
                            "Congratulations!\n\n" +
                            "We are pleased to inform you that you have been shortlisted for the next stage of our recruitment process.\n\n" +
                            "Our recruitment team will contact you soon with further details.\n\n" +
                            "Best Regards,\n" +
                            "HR Team\n" +
                            "Job Portal"
            );
        }

        return updatedApplication;
    }
    public List<Application> getApplicationsByEmail(String email) {

        return applicationRepository.findByEmail(email);
    }

    public List<Application> getApplicationsByJobId(Long jobId) {

        return applicationRepository.findByJobId(jobId);
    }

    public List<Application> getApplicationsByCandidate(String email) {
        return applicationRepository.findByEmail(email);
    }
}