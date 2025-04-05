package com.example.worknexus.Controller;

import com.example.worknexus.Entity.WorkHistory;
import com.example.worknexus.Service.WorkHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/work-history")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkHistoryController {

    @Autowired
    private WorkHistoryService workHistoryService;

    @PostMapping("/add")
    public String addWorkHistory(@RequestParam String email,
                                 @RequestParam String date,
                                 @RequestParam String title,
                                 @RequestParam String description,
                                 @RequestParam String startTime,
                                 @RequestParam String endTime) {
        return workHistoryService.addWorkHistory(
                email,
                LocalDate.parse(date),
                title,
                description,
                LocalTime.parse(startTime),
                LocalTime.parse(endTime)
        );
    }

    @GetMapping("/user/{email}")
    public List<WorkHistory> getUserWorkHistory(@PathVariable String email) {
        return workHistoryService.getUserWorkHistory(email);
    }

    @GetMapping("/all")
    public List<WorkHistory> getAllWorkHistory() {
        return workHistoryService.getAllWorkHistory();
    }

    @GetMapping("/{id}")
    public WorkHistory getWorkHistoryById(@PathVariable Long id) {
        return workHistoryService.getWorkHistoryById(id);
    }

    @PutMapping("/update/{id}")
    public String updateWorkHistory(@PathVariable Long id,
                                    @RequestParam String date,
                                    @RequestParam String title,
                                    @RequestParam String description,
                                    @RequestParam String startTime,
                                    @RequestParam String endTime) {
        return workHistoryService.updateWorkHistory(
                id,
                LocalDate.parse(date),
                title,
                description,
                LocalTime.parse(startTime),
                LocalTime.parse(endTime)
        );
    }

    @GetMapping("/user/{email}/filter")
    public List<WorkHistory> getUserWorkHistoryByDateRange(@PathVariable String email,
                                                           @RequestParam String startDate,
                                                           @RequestParam String endDate) {
        return workHistoryService.getUserWorkHistoryByDateRange(
                email,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
}