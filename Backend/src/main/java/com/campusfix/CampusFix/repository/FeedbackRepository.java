package com.campusfix.CampusFix.repository;

import com.campusfix.CampusFix.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

    boolean existsByComplaintId(Long complaintId);

    List<Feedback> findBySubmittedById(Long userId);
}
