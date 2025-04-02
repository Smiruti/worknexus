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
        if (userRepository.findByEmail(email) == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setRole("EMPLOYEE"); // Default role
            userRepository.save(newUser);
        }
    }

    public String updateUserDetails(Long id, String name, String mobile) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (name != null) user.setName(name);
            if (mobile != null) user.setMobile(mobile);
            userRepository.save(user);
            return "User details updated successfully";
        }
        return "User not found";
    }

    public String updateProfilePic(Long id, String profilePicUrl) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setProfilePicUrl(profilePicUrl);
            userRepository.save(user);
            return "Profile picture updated successfully";
        }
        return "User not found";
    }

    public User viewUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> viewAllUsers() {
        return userRepository.findAll();
    }
}
