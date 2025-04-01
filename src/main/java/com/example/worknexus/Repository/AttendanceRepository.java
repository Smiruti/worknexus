package com.example.worknexus.Repository;

import com.example.worknexus.Entity.Attendance;
import com.example.worknexus.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeAndCheckInTimeBetween(Employee employee, Date startDate, Date endDate);
    Attendance findTopByEmployeeOrderByCheckInTimeDesc(Employee employee);
}