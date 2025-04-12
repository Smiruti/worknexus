package com.example.worknexus.Service;

import com.example.worknexus.Entity.Task;
import com.example.worknexus.Entity.User;
import com.example.worknexus.Repository.TaskRepository;
import com.example.worknexus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(String title, String details, Long assignedToId, Long assignedById) {
        User assignedTo = userRepository.findById(assignedToId).orElse(null);
        User assignedBy = userRepository.findById(assignedById).orElse(null);

        if (assignedTo == null || assignedBy == null) {
            return null;
        }

        Task task = new Task(title, details, assignedTo, assignedBy);
        return taskRepository.save(task);
    }

    public List<Task> getTasksAssignedToUser(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }

    public List<Task> getTasksAssignedByAdmin(Long adminId) {
        return taskRepository.findByAssignedById(adminId);
    }

    public String updateTaskStatus(Long taskId, String status, Long userId) {
        return taskRepository.findById(taskId).map(task -> {
            // Verify the requesting user is the assigned employee
            if (!task.getAssignedTo().getId().equals(userId)) {
                return "Unauthorized: You can only update your own tasks";
            }

            if (status == null || (!status.equalsIgnoreCase("IN_PROGRESS") &&
                    !status.equalsIgnoreCase("COMPLETED"))) {
                return "Invalid status. Allowed values: IN_PROGRESS or COMPLETED";
            }

            task.setStatus(status.toUpperCase());
            taskRepository.save(task);
            return "Task status updated successfully";
        }).orElse("Task not found");
    }
}