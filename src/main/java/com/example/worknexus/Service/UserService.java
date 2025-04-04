package com.example.worknexus.Service;

import com.example.worknexus.Entity.User;
import com.example.worknexus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(String email) {
        if (!userRepository.findByEmail(email).isPresent()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setRole("EMPLOYEE"); // Default role
            userRepository.save(newUser);
        }
    }

    public String updateUserDetails(Long id, String name, String mobile) {
        return userRepository.findById(id).map(user -> {
            if (name != null && !name.isEmpty()) {
                user.setName(name);
            }
            if (mobile != null && !mobile.isEmpty()) {
                user.setMobile(mobile);
            }
            userRepository.save(user);
            return "User details updated successfully";
        }).orElse("User not found");
    }

    public String updateProfilePic(Long id, String profilePicUrl) {
        return userRepository.findById(id).map(user -> {
            if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
                user.setProfilePicUrl(profilePicUrl);
                userRepository.save(user);
                return "Profile picture updated successfully";
            }
            return "Invalid profile picture URL";
        }).orElse("User not found");
    }

    public User viewUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> viewAllUsers() {
        return userRepository.findAll();
    }

    public String updateUserRole(Long id, String role) {
        if (role == null || (!role.equalsIgnoreCase("EMPLOYEE") && !role.equalsIgnoreCase("ADMIN"))) {
            return "Invalid role. Allowed values: EMPLOYEE or ADMIN";
        }

        return userRepository.findById(id).map(user -> {
            user.setRole(role.toUpperCase());
            userRepository.save(user);
            return "User role updated successfully";
        }).orElse("User not found");
    }
}
