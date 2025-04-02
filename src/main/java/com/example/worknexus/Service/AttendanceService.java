package com.example.worknexus.Service;

import com.example.worknexus.Entity.Attendance;
import com.example.worknexus.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Clock-In
    public Attendance clockIn(Long userId) {
        Attendance attendance = new Attendance();
        attendance.setUserId(userId);
        attendance.setClockIn(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    // Clock-Out
    public Attendance clockOut(Long userId) {
        List<Attendance> userAttendance = attendanceRepository.findByUserId(userId);
        if (userAttendance.isEmpty()) {
            throw new RuntimeException("No active clock-in found for this user.");
        }

        Attendance latestAttendance = userAttendance.get(userAttendance.size() - 1);
        if (latestAttendance.getClockOut() != null) {
            throw new RuntimeException("User already clocked out.");
        }

        latestAttendance.setClockOut(LocalDateTime.now());
        return attendanceRepository.save(latestAttendance);
    }

    // Get Attendance by User ID
    public List<Attendance> getUserAttendance(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }
}