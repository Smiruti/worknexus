package com.example.worknexus.Service;

import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Entity.LeaveRequest;
import com.example.worknexus.Repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public LeaveRequest applyForLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus("PENDING");
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest updateLeaveStatus(Long leaveId, String status, String comments) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId).orElse(null);
        if(leaveRequest != null) {
            leaveRequest.setStatus(status);
            leaveRequest.setComments(comments);
            return leaveRequestRepository.save(leaveRequest);
        }
        return null;
    }

    public List<LeaveRequest> getEmployeeLeaves(Employee employee) {
        return leaveRequestRepository.findByEmployee(employee);
    }

    public List<LeaveRequest> getPendingLeaves() {
        return leaveRequestRepository.findByStatus("PENDING");
    }
}
