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
    public LeaveRequest requestLeave(@RequestParam Long userId,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate) {
        return leaveService.requestLeave(userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @PostMapping("/approve/{leaveId}")
    public LeaveRequest approveLeave(@PathVariable Long leaveId) {
        return leaveService.approveLeave(leaveId);
    }

    @PostMapping("/reject/{leaveId}")
    public LeaveRequest rejectLeave(@PathVariable Long leaveId) {
        return leaveService.rejectLeave(leaveId);
    }

    @GetMapping("/user/{userId}")
    public List<LeaveRequest> getUserLeaveHistory(@PathVariable Long userId) {
        return leaveService.getUserLeaveHistory(userId);
    }
}