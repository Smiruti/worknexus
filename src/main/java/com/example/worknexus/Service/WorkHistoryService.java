package com.example.worknexus.Service;

import com.example.worknexus.Entity.User;
import com.example.worknexus.Entity.WorkHistory;
import com.example.worknexus.Repository.UserRepository;
import com.example.worknexus.Repository.WorkHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkHistoryService {

    @Autowired
    private WorkHistoryRepository workHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    public String addWorkHistory(String email, LocalDate date, String title,
                                 String description, LocalTime startTime, LocalTime endTime) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkHistory workHistory = new WorkHistory(date, title, description, startTime, endTime, user);
        workHistoryRepository.save(workHistory);
        return "Work history added successfully";
    }

    public List<WorkHistory> getUserWorkHistory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return workHistoryRepository.findByUserId(user.getId());
    }

    public List<WorkHistory> getAllWorkHistory() {
        return workHistoryRepository.findAll();
    }

    public WorkHistory getWorkHistoryById(Long id) {
        return workHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work history not found"));
    }

    public String updateWorkHistory(Long id, LocalDate date, String title,
                                    String description, LocalTime startTime, LocalTime endTime) {
        WorkHistory workHistory = workHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work history not found"));

        workHistory.setDate(date);
        workHistory.setTitle(title);
        workHistory.setDescription(description);
        workHistory.setStartTime(startTime);
        workHistory.setEndTime(endTime);

        workHistoryRepository.save(workHistory);
        return "Work history updated successfully";
    }

    public List<WorkHistory> getUserWorkHistoryByDateRange(String email, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return workHistoryRepository.findByUserIdAndDateBetween(user.getId(), startDate, endDate);
    }
}