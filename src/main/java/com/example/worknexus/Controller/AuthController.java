package com.example.worknexus.Controller;

import com.example.worknexus.Service.AuthService;
import com.example.worknexus.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        authService.sendOTPEmail(email);
        return "OTP sent successfully to " + email;
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam int otp) {
        if (authService.verifyOTP(email, otp)) {
            userService.createUser(email);  // Create user with email
            return "OTP verified! User registered successfully.";
        }
        return "Invalid OTP. Please try again.";
    }
}
