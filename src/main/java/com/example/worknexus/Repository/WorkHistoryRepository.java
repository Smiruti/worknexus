package com.example.worknexus.Repository;

import com.example.worknexus.Entity.WorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkHistoryRepository extends JpaRepository<WorkHistory, Long> {
    List<WorkHistory> findByUserId(Long userId);
    List<WorkHistory> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}