package com.example.worknexus.Service;

import com.example.worknexus.Entity.LeaveRequest;
import com.example.worknexus.Repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    // Request Leave
    public LeaveRequest requestLeave(Long userId, LocalDate startDate, LocalDate endDate) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserId(userId);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus("PENDING");
        leaveRequest.setRequestDate(LocalDateTime.now());
        return leaveRepository.save(leaveRequest);
    }

    // Approve Leave
    public LeaveRequest approveLeave(Long leaveId) {
        LeaveRequest leaveRequest = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found."));
        leaveRequest.setStatus("APPROVED");
        return leaveRepository.save(leaveRequest);
    }

    // Reject Leave
    public LeaveRequest rejectLeave(Long leaveId) {
        LeaveRequest leaveRequest = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found."));
        leaveRequest.setStatus("REJECTED");
        return leaveRepository.save(leaveRequest);
    }

    // Get Leave History for a User
    public List<LeaveRequest> getUserLeaveHistory(Long userId) {
        return leaveRepository.findByUserId(userId);
    }
}