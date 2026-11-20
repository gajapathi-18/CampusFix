package com.campusfix.CampusFix.repository;

import com.campusfix.CampusFix.entity.Escalation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscalationRepository
        extends JpaRepository<Escalation, Long> {

    boolean existsByComplaintId(Long complaintId);

    List<Escalation> findAllByOrderByEscalatedAtDesc();
}
