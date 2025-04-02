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

    // Request Leave
    public String requestLeave(Long userId, LocalDate leaveDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getLeaveBalance() <= 0) {
            return "You have exhausted your leave balance.";
        }

        LeaveRequest leaveRequest = new LeaveRequest(user, leaveDate, "PENDING", LocalDateTime.now());
        leaveRepository.save(leaveRequest);
        return "Leave request submitted successfully.";
    }

    // Approve Leave (Only Admins)
    public String approveLeave(Long leaveId, Long adminId) {
        User admin = userRepository.findById(adminId)
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

    // Reject Leave
    public String rejectLeave(Long leaveId, Long adminId) {
        User admin = userRepository.findById(adminId)
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

    // Get Leave History for a User
    public List<LeaveRequest> getUserLeaveHistory(Long userId) {
        return leaveRepository.findByUserId(userId);
    }

    // Get all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRepository.findAll();
    }

}
