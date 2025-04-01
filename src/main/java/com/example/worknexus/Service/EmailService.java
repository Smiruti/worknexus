package com.example.worknexus.Service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    // In a real application, implement email sending logic
    public void sendEmail(String to, String subject, String body) {
        // Implementation using JavaMailSender or other email service
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}