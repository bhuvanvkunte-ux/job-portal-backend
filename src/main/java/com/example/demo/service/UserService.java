package com.example.demo.service;

import com.example.demo.config.JwtUtil;
import com.example.demo.dto.ForgotPasswordRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.ProfileUpdateRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.ResetPasswordRequest;
import com.example.demo.dto.VerifyOtpRequest;
import com.example.demo.entity.Otp;
import com.example.demo.entity.User;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    // ================= REGISTER =================

    public User registerUser(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                encoder.encode(request.getPassword())
        );

        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    // ================= GET USERS =================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ================= LOGIN =================

    public LoginResponse loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        if (!encoder.matches(password, user.getPassword())) {
            return null;
        }

        String token =
                jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole()
                );

        return new LoginResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    // ================= PROFILE =================

    public User updateProfile(
            String email,
            ProfileUpdateRequest request
    ) {

        User user = userRepository.findByEmail(email);

        if (user != null) {

            user.setPhone(request.getPhone());
            user.setSkills(request.getSkills());
            user.setLocation(request.getLocation());

            return userRepository.save(user);
        }

        return null;
    }

    public User updateProfile(
            Long id,
            ProfileUpdateRequest request
    ) {

        User user =
                userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setSkills(request.getSkills());
        user.setLocation(request.getLocation());

        return userRepository.save(user);
    }

    public User updateProfile(
            String email,
            User updatedUser
    ) {

        User existingUser =
                userRepository.findByEmail(email);

        if (existingUser == null) {
            return null;
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setLocation(updatedUser.getLocation());
        existingUser.setSkills(updatedUser.getSkills());

        return userRepository.save(existingUser);
    }

    public User getProfile(String email) {
        return userRepository.findByEmail(email);
    }

    // ================= SEARCH =================

    public List<User> searchCandidatesBySkill(String skill) {
        return userRepository.findBySkillsContaining(skill);
    }

    // ================= USER =================

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ================= FORGOT PASSWORD =================

    public String forgotPassword(ForgotPasswordRequest request) {

        User user =
                userRepository.findByEmail(request.getEmail());

        if (user == null) {
            return "Email not registered";
        }

        Random random = new Random();

        String otp =
                String.valueOf(
                        100000 + random.nextInt(900000)
                );

        Otp otpEntity =
                otpRepository.findByEmail(request.getEmail());

        if (otpEntity == null) {
            otpEntity = new Otp();
        }

        otpEntity.setEmail(request.getEmail());
        otpEntity.setOtp(otp);

        otpEntity.setExpiryTime(
                LocalDateTime.now()
                        .plusMinutes(5)
                        .toString()
        );

        otpRepository.save(otpEntity);

        emailService.sendEmail(
                request.getEmail(),
                "Password Reset OTP",
                "Your OTP for password reset is: "
                        + otp
                        + "\n\nThis OTP is valid for 5 minutes."
        );

        return "OTP Sent Successfully";
    }

    // ================= VERIFY OTP =================

    public String verifyOtp(VerifyOtpRequest request) {

        Otp otpEntity =
                otpRepository.findByEmailAndOtp(
                        request.getEmail(),
                        request.getOtp()
                );

        if (otpEntity == null) {
            return "Invalid OTP";
        }

        LocalDateTime expiry =
                LocalDateTime.parse(
                        otpEntity.getExpiryTime()
                );

        if (LocalDateTime.now().isAfter(expiry)) {
            return "OTP Expired";
        }

        return "OTP Verified Successfully";
    }

    // ================= RESET PASSWORD =================

    public String resetPassword(ResetPasswordRequest request) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                );

        if (user == null) {
            return "User Not Found";
        }

        if (
                request.getNewPassword() == null ||
                        request.getNewPassword().isEmpty()
        ) {
            return "New password is required";
        }

        user.setPassword(
                encoder.encode(request.getNewPassword())
        );

        userRepository.save(user);

        Otp otpEntity =
                otpRepository.findByEmail(request.getEmail());

        if (otpEntity != null) {
            otpRepository.delete(otpEntity);
        }

        return "Password Reset Successfully";
    }
}