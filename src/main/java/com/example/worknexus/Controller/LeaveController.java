package com.example.worknexus.Controller;

import com.example.worknexus.Entity.LeaveRequest;
import com.example.worknexus.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping("/request")
    public String requestLeave(@RequestParam Long userId, @RequestParam String leaveDate) {
        return leaveService.requestLeave(userId, LocalDate.parse(leaveDate));
    }

    @PostMapping("/approve/{leaveId}")
    public String approveLeave(@PathVariable Long leaveId, @RequestParam Long adminId) {
        return leaveService.approveLeave(leaveId, adminId);
    }

    @PostMapping("/reject/{leaveId}")
    public String rejectLeave(@PathVariable Long leaveId, @RequestParam Long adminId) {
        return leaveService.rejectLeave(leaveId, adminId);
    }

    @GetMapping("/user/{userId}")
    public List<LeaveRequest> getUserLeaveHistory(@PathVariable Long userId) {
        return leaveService.getUserLeaveHistory(userId);
    }

    // View all leave requests (Admin only)
    @GetMapping("/all")
    public List<LeaveRequest> viewAllRequests() {
        return leaveService.getAllLeaveRequests();
    }

}
