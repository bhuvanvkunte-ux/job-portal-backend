package com.example.demo.controller;

import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test-email")
    public String testEmail() {

        emailService.sendEmail(
                "bvkunte@gmail.com",
                "Job Portal Test",
                "Email Service Working Successfully!"
        );

        return "Mail Sent";
    }
}