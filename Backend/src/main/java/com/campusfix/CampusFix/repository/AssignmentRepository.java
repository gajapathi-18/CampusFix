package com.campusfix.CampusFix.repository;

import com.campusfix.CampusFix.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    List<Assignment> findByComplaintIdOrderByAssignedAtDesc(
            Long complaintId);

    List<Assignment> findByMaintenanceUserIdOrderByAssignedAtDesc(
            Long userId);

    List<Assignment> findAllByOrderByAssignedAtDesc();

    boolean existsByComplaintId(Long complaintId);
}
