package com.example.worknexus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private JavaMailSender mailSender;

    private final Random random = new Random();
    private final Map<String, Integer> otpStorage = new HashMap<>();  // Stores OTPs temporarily

    public int generateOTP() {
        return 100000 + random.nextInt(900000);
    }

    public void sendOTPEmail(String email) {
        int otp = generateOTP();
        otpStorage.put(email, otp); // Store OTP for verification

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for WorkNexus Registration");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

    public boolean verifyOTP(String email, int otp) {
        if (otpStorage.containsKey(email) && otpStorage.get(email) == otp) {
            otpStorage.remove(email);  // Remove OTP after successful verification
            return true;
        }
        return false;
    }
}
