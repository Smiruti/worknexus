package com.example.worknexus.Controller;

import com.example.worknexus.Entity.Attendance;
import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkin/{employeeId}")
    public Attendance checkIn(@PathVariable Integer employeeId, @RequestParam String location) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        return attendanceService.checkIn(employee, location);
    }

    @PostMapping("/checkout/{employeeId}")
    public Attendance checkOut(@PathVariable Integer employeeId) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        return attendanceService.checkOut(employee);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getEmployeeAttendance(
            @PathVariable Integer employeeId,
            @RequestParam Date from,
            @RequestParam Date to) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        return attendanceService.getEmployeeAttendance(employee, from, to);
    }
}