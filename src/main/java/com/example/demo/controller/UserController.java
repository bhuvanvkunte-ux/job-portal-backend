package com.example.demo.controller;

import com.example.demo.dto.ForgotPasswordRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.ResetPasswordRequest;
import com.example.demo.dto.VerifyOtpRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Tag(
        name = "User APIs",
        description = "User Registration, Login and Profile Operations"
)

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ================= REGISTER =================

    @Operation(summary = "Register New User")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterRequest request
    ) {
        User savedUser = userService.registerUser(request);

        return ResponseEntity.ok(savedUser);
    }

    // ================= LOGIN =================

    @Operation(summary = "User Login")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginRequest loginRequest
    ) {
        LoginResponse response = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (response == null) {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }

        return ResponseEntity.ok(response);
    }

    // ================= GET ALL USERS =================

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    // ================= GET PROFILE =================

    @Operation(summary = "Get User Profile")
    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getProfile(
            @PathVariable String email
    ) {
        User user = userService.getProfile(email);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user);
    }

    // ================= UPDATE PROFILE =================

    @PutMapping("/profile/{email}")
    public ResponseEntity<?> updateProfile(
            @PathVariable String email,
            @RequestBody User updatedUser
    ) {
        User user = userService.updateProfile(email, updatedUser);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user);
    }

    // ================= SEARCH CANDIDATES =================

    @Operation(summary = "Search Candidates By Skill")
    @GetMapping("/search-candidates")
    public ResponseEntity<?> searchCandidates(
            @RequestParam String skill
    ) {
        List<User> users = userService.searchCandidatesBySkill(skill);

        return ResponseEntity.ok(users);
    }

    // ================= UPLOAD RESUME =================

    @PostMapping("/upload-resume/{userId}")
    public ResponseEntity<?> uploadResume(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            User user = userService.getUserById(userId);

            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload a valid file");
            }

            String uploadDir =
                    System.getProperty("user.dir") + "/resumes/";

            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath =
                    uploadDir + userId + "_" + file.getOriginalFilename();

            System.out.println("Saving Resume To: " + filePath);

            file.transferTo(new File(filePath));

            user.setResumePath(filePath);

            userService.saveUser(user);

            return ResponseEntity.ok("Resume Uploaded Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Resume upload failed");
        }
    }

    // ================= DOWNLOAD RESUME BY USER ID =================

    @GetMapping("/resume/{userId}")
    public ResponseEntity<?> downloadResume(
            @PathVariable Long userId
    ) {
        try {
            User user = userService.getUserById(userId);

            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            if (user.getResumePath() == null || user.getResumePath().isEmpty()) {
                return ResponseEntity.status(404).body("Resume not uploaded");
            }

            Path filePath = Paths.get(user.getResumePath());

            if (!Files.exists(filePath)) {
                return ResponseEntity.status(404).body("Resume file not found");
            }

            Resource resource = new UrlResource(filePath.toUri());

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    filePath.getFileName().toString() + "\""
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to download resume");
        }
    }

    // ================= DOWNLOAD RESUME BY EMAIL =================

    @GetMapping("/resume/by-email/{email}")
    public ResponseEntity<?> downloadResumeByEmail(
            @PathVariable String email
    ) {
        try {
            User user = userService.getUserByEmail(email);

            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            if (user.getResumePath() == null || user.getResumePath().isEmpty()) {
                return ResponseEntity.status(404).body("Resume not uploaded");
            }

            Path filePath = Paths.get(user.getResumePath());

            if (!Files.exists(filePath)) {
                return ResponseEntity.status(404).body("Resume file not found");
            }

            Resource resource = new UrlResource(filePath.toUri());

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    filePath.getFileName().toString() + "\""
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to download resume");
        }
    }

    // ================= FORGOT PASSWORD =================

    @Operation(summary = "Forgot Password - Generate OTP")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {
        String result = userService.forgotPassword(request);

        if (result.equals("Email not registered")) {
            return ResponseEntity.status(404).body(result);
        }

        return ResponseEntity.ok(result);
    }

    // ================= VERIFY OTP =================

    @Operation(summary = "Verify OTP")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequest request
    ) {
        String result = userService.verifyOtp(request);

        if (result.equals("Invalid OTP") || result.equals("OTP Expired")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    // ================= RESET PASSWORD =================

    @Operation(summary = "Reset Password")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        String result = userService.resetPassword(request);

        if (
                result.equals("User Not Found") ||
                        result.equals("New password is required")
        ) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
