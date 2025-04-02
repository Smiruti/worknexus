package com.example.worknexus.Controller;

import com.example.worknexus.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        int otp = authService.generateOTP();
        authService.sendOTPEmail(email, otp);
        return "OTP sent successfully";
    }
}