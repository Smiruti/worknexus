package com.example.worknexus.Controller;

import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Entity.LeaveRequest;
import com.example.worknexus.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @PostMapping("/apply")
    public LeaveRequest applyForLeave(@RequestBody LeaveRequest leaveRequest) {
        return leaveService.applyForLeave(leaveRequest);
    }

    @PutMapping("/{leaveId}/status")
    public LeaveRequest updateLeaveStatus(
            @PathVariable Long leaveId,
            @RequestParam String status,
            @RequestParam(required = false) String comments) {
        return leaveService.updateLeaveStatus(leaveId, status, comments);
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getEmployeeLeaves(@PathVariable Integer employeeId) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        return leaveService.getEmployeeLeaves(employee);
    }

    @GetMapping("/pending")
    public List<LeaveRequest> getPendingLeaves() {
        return leaveService.getPendingLeaves();
    }
}
