package com.example.worknexus.Service;

import com.example.worknexus.Entity.Attendance;
import com.example.worknexus.Entity.User;
import com.example.worknexus.Repository.AttendanceRepository;
import com.example.worknexus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    // ⏰ Scheduled Task to Auto-Create Attendance Records at 12:00 AM IST
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kolkata")
    public void createDailyAttendance() {
        List<User> allUsers = userRepository.findAll();
        LocalDate today = LocalDate.now();

        for (User user : allUsers) {
            Optional<Attendance> existingAttendance = attendanceRepository.findByUserAndAttendanceDate(user, today);
            if (existingAttendance.isEmpty()) {
                // Create attendance entry with only the date set
                Attendance attendance = new Attendance();
                attendance.setUser(user);
                attendance.setAttendanceDate(today);
                attendance.setStatus("ABSENT"); // Default status
                attendanceRepository.save(attendance);
            }
        }
    }

    // ⏰ Clock-In Method (Now Updates Existing Record Instead of Creating a New One)
    public Attendance clockIn(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        LocalDate today = LocalDate.now();

        // Check if an attendance record exists for today
        Optional<Attendance> existingAttendance = attendanceRepository.findByUserAndAttendanceDate(user, today);
        if (existingAttendance.isEmpty()) {
            throw new RuntimeException("Attendance record for today is missing. Please contact admin.");
        }

        // Update existing record with clock-in time
        Attendance attendance = existingAttendance.get();
        if (attendance.getClockIn() != null) {
            throw new RuntimeException("User has already clocked in today.");
        }

        attendance.setClockIn(LocalDateTime.now());
        attendance.setStatus("PRESENT");
        return attendanceRepository.save(attendance);
    }

    public Attendance clockOut(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        LocalDate today = LocalDate.now();

        Optional<Attendance> existingAttendance = attendanceRepository.findByUserAndAttendanceDate(user, today);
        if (existingAttendance.isEmpty()) {
            throw new RuntimeException("Attendance record for today is missing. Please contact admin.");
        }

        Attendance attendance = existingAttendance.get();
        if (attendance.getClockOut() != null) {
            throw new RuntimeException("User has already clocked out today.");
        }

        attendance.setClockOut(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    // 📅 View All Attendance Records for a User
    public List<Attendance> viewAttendanceByUser(Long userId) {
        return attendanceRepository.findByUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }
}

