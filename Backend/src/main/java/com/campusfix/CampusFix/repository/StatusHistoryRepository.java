package com.campusfix.CampusFix.repository;

import com.campusfix.CampusFix.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHistoryRepository
        extends JpaRepository<StatusHistory, Long> {

    List<StatusHistory> findByComplaintIdOrderByChangedAtDesc(
            Long complaintId);
}
