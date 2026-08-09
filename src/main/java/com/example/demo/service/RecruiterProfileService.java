package com.example.demo.service;

import com.example.demo.entity.RecruiterProfile;
import com.example.demo.repository.RecruiterProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruiterProfileService {

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfile saveProfile(RecruiterProfile profile) {

        RecruiterProfile existingProfile =
                recruiterProfileRepository.findByRecruiterEmail(
                        profile.getRecruiterEmail()
                );

        if (existingProfile != null) {

            existingProfile.setCompanyName(
                    profile.getCompanyName()
            );

            existingProfile.setCompanyWebsite(
                    profile.getCompanyWebsite()
            );

            existingProfile.setCompanyDescription(
                    profile.getCompanyDescription()
            );

            existingProfile.setCompanyLocation(
                    profile.getCompanyLocation()
            );

            return recruiterProfileRepository.save(existingProfile);
        }

        return recruiterProfileRepository.save(profile);
    }

    public RecruiterProfile getProfile(String recruiterEmail) {

        return recruiterProfileRepository.findByRecruiterEmail(
                recruiterEmail
        );
    }

    public RecruiterProfile updateProfile(RecruiterProfile profile) {

        RecruiterProfile existingProfile =
                recruiterProfileRepository.findByRecruiterEmail(
                        profile.getRecruiterEmail()
                );

        if (existingProfile == null) {
            return recruiterProfileRepository.save(profile);
        }

        existingProfile.setCompanyName(
                profile.getCompanyName()
        );

        existingProfile.setCompanyWebsite(
                profile.getCompanyWebsite()
        );

        existingProfile.setCompanyDescription(
                profile.getCompanyDescription()
        );

        existingProfile.setCompanyLocation(
                profile.getCompanyLocation()
        );

        return recruiterProfileRepository.save(existingProfile);
    }
}