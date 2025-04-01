package com.example.worknexus.Service;

import com.example.worknexus.Entity.Attendance;
import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance checkIn(Employee employee, String location) {
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCheckInTime(new Date());
        attendance.setLocation(location);
        attendance.setStatus("PRESENT");
        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(Employee employee) {
        Attendance lastAttendance = attendanceRepository.findTopByEmployeeOrderByCheckInTimeDesc(employee);
        if(lastAttendance != null && lastAttendance.getCheckOutTime() == null) {
            lastAttendance.setCheckOutTime(new Date());
            return attendanceRepository.save(lastAttendance);
        }
        return null;
    }

    public List<Attendance> getEmployeeAttendance(Employee employee, Date startDate, Date endDate) {
        return attendanceRepository.findByEmployeeAndCheckInTimeBetween(employee, startDate, endDate);
    }
}