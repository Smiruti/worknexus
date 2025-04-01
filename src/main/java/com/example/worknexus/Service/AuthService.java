package com.example.worknexus.Service;

import com.example.worknexus.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    private Map<String, String> otpStorage = new HashMap<>();

    public String generateOTP(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if(employee == null) {
            return "User not found";
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);

        // Send OTP via email
        emailService.sendEmail(email, "Your OTP for WorkNexus", "Your OTP is: " + otp);

        return "OTP sent successfully";
    }

    public String verifyOTP(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        if(storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email);
            return "OTP verified successfully";
        }
        return "Invalid OTP";
    }
}