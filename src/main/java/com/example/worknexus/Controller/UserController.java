package com.example.worknexus.Controller;

import com.example.worknexus.Entity.User;
import com.example.worknexus.Service.CloudinaryService;
import com.example.worknexus.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserService userService;

    @PutMapping("/update-details")
    public String updateUserDetails(@RequestParam Long id,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String mobile) {
        return userService.updateUserDetails(id, name, mobile);
    }

    @PostMapping("/upload-profile-pic")
    public String uploadProfilePic(@RequestParam Long id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            return userService.updateProfilePic(id, imageUrl);
        } catch (IOException e) {
            return "Error uploading file";
        }
    }

    @GetMapping("/{id}")
    public User viewUserById(@PathVariable Long id) {
        return userService.viewUserById(id);
    }

    @GetMapping("/find-by-email")
    public User findUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/all")
    public List<User> viewAllUsers(){
        return userService.viewAllUsers();
    }

    @PutMapping("/update-role")
    public String updateUserRole(@RequestParam Long id, @RequestParam String role) {
        return userService.updateUserRole(id, role);
    }

}