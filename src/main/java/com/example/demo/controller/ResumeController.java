package com.example.demo.controller;

import com.example.demo.entity.Resume;
import com.example.demo.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public String uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("email") String email) {

        try {

            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File destination = new File(dir, file.getOriginalFilename());

            file.transferTo(destination);

            Resume resume = new Resume();
            resume.setFileName(file.getOriginalFilename());
            resume.setFilePath(destination.getAbsolutePath());
            resume.setCandidateEmail(email);

            resumeService.saveResume(resume);

            return "Resume Uploaded Successfully";

        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}