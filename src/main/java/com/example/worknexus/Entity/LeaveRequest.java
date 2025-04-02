package com.example.worknexus.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate leaveDate;
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDateTime requestDate;

    public LeaveRequest() {}

    public LeaveRequest(User user, LocalDate leaveDate, String status, LocalDateTime requestDate) {
        this.user = user;
        this.leaveDate = leaveDate;
        this.status = status;
        this.requestDate = requestDate;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
