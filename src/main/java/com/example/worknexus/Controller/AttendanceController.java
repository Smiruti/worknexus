package com.example.worknexus.Controller;

import com.example.worknexus.Entity.Attendance;
import com.example.worknexus.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/clock-in")
    public Attendance clockIn(@RequestParam Long userId) {
        return attendanceService.clockIn(userId);
    }

    @PostMapping("/clock-out")
    public Attendance clockOut(@RequestParam Long userId) {
        return attendanceService.clockOut(userId);
    }

    @GetMapping("/user/{userId}")
    public List<Attendance> getUserAttendance(@PathVariable Long userId) {
        return attendanceService.getUserAttendance(userId);
    }
}