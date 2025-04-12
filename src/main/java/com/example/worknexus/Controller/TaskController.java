package com.example.worknexus.Controller;

import com.example.worknexus.Entity.Task;
import com.example.worknexus.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestParam String title,
                           @RequestParam String details,
                           @RequestParam Long assignedToId,
                           @RequestParam Long assignedById) {
        return taskService.createTask(title, details, assignedToId, assignedById);
    }

    @GetMapping("/employee/{userId}")
    public List<Task> getEmployeeTasks(@PathVariable Long userId) {
        return taskService.getTasksAssignedToUser(userId);
    }

    @GetMapping("/admin/{adminId}")
    public List<Task> getAdminTasks(@PathVariable Long adminId) {
        return taskService.getTasksAssignedByAdmin(adminId);
    }

    @PutMapping("/update-status")
    public String updateTaskStatus(@RequestParam Long taskId,
                                   @RequestParam String status,
                                   @RequestParam Long userId) {
        return taskService.updateTaskStatus(taskId, status, userId);
    }
}