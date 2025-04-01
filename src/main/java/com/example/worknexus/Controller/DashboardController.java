package com.example.worknexus.Controller;

import com.example.worknexus.Entity.*;
import com.example.worknexus.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private LeaveService leaveService;

    @GetMapping("/employee/{employeeId}")
    public Map<String, Object> getEmployeeDashboard(@PathVariable Integer employeeId) {
        Map<String, Object> dashboard = new HashMap<>();

        Employee employee = employeeService.viewEmployeeById(employeeId);

        // Get today's attendance status
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date todayStart = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date todayEnd = calendar.getTime();

        List<Attendance> todayAttendance = attendanceService.getEmployeeAttendance(
                employee, todayStart, todayEnd);

        // Get leave balance
        dashboard.put("employee", employee);
        dashboard.put("todayAttendance", todayAttendance.isEmpty() ? null : todayAttendance.get(0));
        dashboard.put("leaveBalance", employee.getLeaveBalance());
        dashboard.put("pendingLeaves", leaveService.getEmployeeLeaves(employee).stream()
                .filter(l -> "PENDING".equals(l.getStatus()))
                .count());

        return dashboard;
    }

    @GetMapping("/admin")
    public Map<String, Object> getAdminDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalEmployees", employeeService.viewAll().size());
        dashboard.put("pendingLeaveRequests", leaveService.getPendingLeaves().size());

        // Add more admin statistics as needed

        return dashboard;
    }
}