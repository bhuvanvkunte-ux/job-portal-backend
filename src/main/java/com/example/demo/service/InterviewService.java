package com.example.demo.service;

import com.example.demo.entity.Interview;
import com.example.demo.repository.InterviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private EmailService emailService;

    public Interview scheduleInterview(Interview interview) {

        if (interview.getStatus() == null || interview.getStatus().isEmpty()) {
            interview.setStatus("SCHEDULED");
        }

        Interview savedInterview =
                interviewRepository.save(interview);

        String subject = "Interview Scheduled - Job Portal";

        String body =
                "Hello Candidate,\n\n"
                        + "Your interview has been scheduled.\n\n"
                        + "Interview Details:\n"
                        + "Application ID: " + interview.getApplicationId() + "\n"
                        + "Date: " + interview.getInterviewDate() + "\n"
                        + "Time: " + interview.getInterviewTime() + "\n"
                        + "Mode: " + interview.getMode() + "\n"
                        + "Status: " + interview.getStatus() + "\n\n"
                        + "Please be available on time.\n\n"
                        + "Regards,\n"
                        + "Job Portal Team";

        emailService.sendEmail(
                interview.getCandidateEmail(),
                subject,
                body
        );

        return savedInterview;
    }

    public List<Interview> getInterviewsByCandidateEmail(
            String candidateEmail
    ) {
        return interviewRepository.findByCandidateEmail(candidateEmail);
    }

    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    public Interview updateInterviewStatus(
            Long id,
            String status
    ) {
        Interview interview =
                interviewRepository.findById(id).orElse(null);

        if (interview == null) {
            return null;
        }

        interview.setStatus(status);

        Interview updatedInterview =
                interviewRepository.save(interview);

        String subject = "Interview Status Updated - Job Portal";
        String body =
                "Hello Candidate,\n\n"
                        + "Your interview status has been updated.\n\n"
                        + "Application ID: " + interview.getApplicationId() + "\n"
                        + "Date: " + interview.getInterviewDate() + "\n"
                        + "Time: " + interview.getInterviewTime() + "\n"
                        + "Mode: " + interview.getMode() + "\n"
                        + "New Status: " + interview.getStatus() + "\n\n"
                        + "Regards,\n"
                        + "Job Portal Team";
        emailService.sendEmail(
                interview.getCandidateEmail(),
                subject,
                body
        );
        return updatedInterview;
    }
}