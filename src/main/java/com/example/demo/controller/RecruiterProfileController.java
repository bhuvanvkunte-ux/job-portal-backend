package com.example.demo.controller;

import com.example.demo.entity.RecruiterProfile;
import com.example.demo.service.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    @Autowired
    private RecruiterProfileService recruiterProfileService;

    @PostMapping
    public RecruiterProfile saveProfile(
            @RequestBody RecruiterProfile profile) {

        return recruiterProfileService.saveProfile(profile);
    }

    @GetMapping("/{email}")
    public RecruiterProfile getProfile(
            @PathVariable String email) {

        return recruiterProfileService.getProfile(email);
    }

    @PutMapping
    public RecruiterProfile updateProfile(
            @RequestBody RecruiterProfile profile) {

        return recruiterProfileService
                .updateProfile(profile);
    }
}