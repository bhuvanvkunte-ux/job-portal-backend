package com.example.demo.service;

import com.example.demo.entity.SavedJob;
import com.example.demo.repository.SavedJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedJobService {

    @Autowired
    private SavedJobRepository savedJobRepository;

    public SavedJob saveJob(SavedJob savedJob) {

        boolean alreadySaved = savedJobRepository.existsByCandidateEmailAndJobId(
                savedJob.getCandidateEmail(),
                savedJob.getJobId()
        );

        if (alreadySaved) {
            throw new RuntimeException("Already saved this job");
        }

        return savedJobRepository.save(savedJob);
    }

    public List<SavedJob> getSavedJobs(String email) {
        return savedJobRepository.findByCandidateEmail(email);
    }

    public void removeSavedJob(Long id) {

        System.out.println("Deleting ID = " + id);

        savedJobRepository.deleteById(id);

        System.out.println("Delete Completed");
    }
}