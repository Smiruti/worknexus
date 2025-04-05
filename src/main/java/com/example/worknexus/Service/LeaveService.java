package com.example.worknexus.Service;

import com.example.worknexus.Entity.LeaveRequest;
import com.example.worknexus.Entity.User;
import com.example.worknexus.Repository.LeaveRepository;
import com.example.worknexus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    public String requestLeave(String email, LocalDate leaveDate, String reason) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getLeaveBalance() <= 0) {
            return "You have exhausted your leave balance.";
        }

        LeaveRequest leaveRequest = new LeaveRequest(user, leaveDate, "PENDING",reason, LocalDateTime.now());
        leaveRepository.save(leaveRequest);
        return "Leave request submitted successfully.";
    }

    public String approveLeave(Long leaveId, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (!"ADMIN".equals(admin.getRole())) {
            return "Only ADMIN can approve leave requests.";
        }

        LeaveRequest leaveRequest = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found."));

        if (!"PENDING".equals(leaveRequest.getStatus())) {
            return "Leave request is already processed.";
        }

        User user = leaveRequest.getUser();
        if (user.getLeaveBalance() > 0) {
            user.setLeaveBalance(user.getLeaveBalance() - 1);
            userRepository.save(user);
        } else {
            return "Leave balance is insufficient.";
        }

        leaveRequest.setStatus("APPROVED");
        leaveRepository.save(leaveRequest);
        return "Leave request approved.";
    }

    public String rejectLeave(Long leaveId, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (!"ADMIN".equals(admin.getRole())) {
            return "Only ADMIN can reject leave requests.";
        }

        LeaveRequest leaveRequest = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found."));

        if (!"PENDING".equals(leaveRequest.getStatus())) {
            return "Leave request is already processed.";
        }

        leaveRequest.setStatus("REJECTED");
        leaveRepository.save(leaveRequest);
        return "Leave request rejected.";
    }

    public List<LeaveRequest> getUserLeaveHistory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return leaveRepository.findByUserId(user.getId());
    }

    // Get all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRepository.findAll();
    }

}
