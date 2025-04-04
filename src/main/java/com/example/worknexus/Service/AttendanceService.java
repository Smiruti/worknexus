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

    // 🕛 Scheduled Task to Auto-Create Attendance Records at 12:00 AM IST
//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kolkata")
//    public void createDailyAttendance() {
//        List<User> allUsers = userRepository.findAll();
//        LocalDate today = LocalDate.now();
//
//        for (User user : allUsers) {
//            Optional<Attendance> existingAttendance = attendanceRepository.findByUserAndAttendanceDate(user, today);
//            if (existingAttendance.isEmpty()) {
//                Attendance attendance = new Attendance();
//                attendance.setUser(user);
//                attendance.setAttendanceDate(today);
//                attendance.setStatus("ABSENT"); // Default status
//                attendanceRepository.save(attendance);
//            }
//        }
//    }

    // 🕛 Scheduled Task to Auto-Update Attendance Status at 11:59 PM IST
//    @Scheduled(cron = "0 59 23 * * ?", zone = "Asia/Kolkata")
//    public void updateDailyAttendanceStatus() {
//        LocalDate today = LocalDate.now();
//        List<Attendance> attendances = attendanceRepository.findAll();
//
//        for (Attendance attendance : attendances) {
//            if (attendance.getAttendanceDate().equals(today)) {
//                // If clock-in exists, mark as PRESENT; otherwise, remain ABSENT
//                if (attendance.getClockIn() != null) {
//                    attendance.setStatus("PRESENT");
//                } else {
//                    attendance.setStatus("ABSENT");
//                }
//                attendanceRepository.save(attendance);
//            }
//        }
//    }
//


    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kolkata")
    public void createDailyAttendance() {
        System.out.println("Running Scheduled Task: Creating Attendance Records for " + LocalDate.now());

        List<User> allUsers = userRepository.findAll();
        LocalDate today = LocalDate.now();

        for (User user : allUsers) {
            Optional<Attendance> existingAttendance = attendanceRepository.findByUserAndAttendanceDate(user, today);
            if (existingAttendance.isEmpty()) {
                Attendance attendance = new Attendance();
                attendance.setUser(user);
                attendance.setAttendanceDate(today);
                attendance.setStatus("ABSENT"); // Default status
                attendanceRepository.save(attendance);
                System.out.println("Created attendance for: " + user.getId());
            }
        }
    }

    public Attendance clockIn(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
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
        if (attendance.getClockIn() != null) {
            throw new RuntimeException("User has already clocked in today.");
        }

        attendance.setClockIn(LocalDateTime.now());
        attendance.setStatus("PRESENT");
        return attendanceRepository.save(attendance);
    }

    public Attendance clockOut(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
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

    public List<Attendance> viewAttendanceByUser(String email) {
        return attendanceRepository.findByUser(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<Attendance> getTodayAttendanceStatus() {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findAll()
                .stream()
                .filter(attendance -> attendance.getAttendanceDate().equals(today))
                .toList();
    }


}
