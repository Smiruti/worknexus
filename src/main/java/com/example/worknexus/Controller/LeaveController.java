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
    public String requestLeave(@RequestParam String email,
                               @RequestParam String leaveDate,
                               @RequestParam String reason) {
        return leaveService.requestLeave(email, LocalDate.parse(leaveDate), reason);
    }

    @PostMapping("/approve/{leaveId}")
    public String approveLeave(@PathVariable Long leaveId, @RequestParam String adminEmail) {
        return leaveService.approveLeave(leaveId, adminEmail);
    }

    @PostMapping("/reject/{leaveId}")
    public String rejectLeave(@PathVariable Long leaveId, @RequestParam String adminEmail) {
        return leaveService.rejectLeave(leaveId, adminEmail);
    }

    @GetMapping("/user/{email}")
    public List<LeaveRequest> getUserLeaveHistory(@PathVariable String email) {
        return leaveService.getUserLeaveHistory(email);
    }

    @GetMapping("/all")
    public List<LeaveRequest> viewAllRequests() {
        return leaveService.getAllLeaveRequests();
    }

}
