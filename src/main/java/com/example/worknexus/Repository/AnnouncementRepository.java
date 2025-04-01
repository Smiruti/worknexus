package com.example.worknexus.Repository;

import com.example.worknexus.Entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByIsImportantTrueOrderByPublishDateDesc();
    List<Announcement> findAllByOrderByPublishDateDesc();
}