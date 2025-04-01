package com.example.worknexus.Service;

import com.example.worknexus.Entity.Announcement;
import com.example.worknexus.Repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement createAnnouncement(Announcement announcement) {
        announcement.setPublishDate(new Date());
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getImportantAnnouncements() {
        return announcementRepository.findByIsImportantTrueOrderByPublishDateDesc();
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAllByOrderByPublishDateDesc();
    }
}