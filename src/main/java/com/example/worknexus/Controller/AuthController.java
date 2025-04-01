package com.example.worknexus.Controller;

import com.example.worknexus.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/request-otp")
    public String requestOtp(@RequestParam String email) {
        return authService.generateOTP(email);
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return authService.verifyOTP(email, otp);
    }
}