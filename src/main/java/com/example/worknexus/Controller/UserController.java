package com.example.worknexus.Controller;

import com.example.worknexus.Service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload-profile-pic")
    public String uploadProfilePic(@RequestParam("file") MultipartFile file) {
        try {
            return cloudinaryService.uploadFile(file);
        } catch (IOException e) {
            return "Error uploading file";
        }
    }
}