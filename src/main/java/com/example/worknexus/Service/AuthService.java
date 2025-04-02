package com.example.worknexus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private JavaMailSender mailSender;

    private final Random random = new Random();

    public int generateOTP() {
        return 100000 + random.nextInt(900000);
    }

    public void sendOTPEmail(String email, int otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for WorkNexus Login");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }
}