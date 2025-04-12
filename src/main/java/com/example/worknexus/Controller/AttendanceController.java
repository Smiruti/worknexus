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
    public Attendance clockIn(@RequestParam String email) {
        return attendanceService.clockIn(email);
    }

    @PostMapping("/clock-out")
    public Attendance clockOut(@RequestParam String email) {
        return attendanceService.clockOut(email);
    }

    @GetMapping("/view/{email}")
    public List<Attendance> viewAttendanceByUser(@PathVariable String email) {
        return attendanceService.viewAttendanceByUser(email);
    }

    @GetMapping("/create-daily")
    public String createDailyAttendance() {
        attendanceService.createDailyAttendance();
        return "Attendance records for today created successfully!";
    }

    @GetMapping("/today-status")
    public List<Attendance> getTodayAttendanceStatus() {
        return attendanceService.getTodayAttendanceStatus();
    }

}
