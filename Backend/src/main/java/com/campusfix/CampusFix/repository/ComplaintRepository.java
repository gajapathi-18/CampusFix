package com.campusfix.CampusFix.repository;

import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.enums.ComplaintStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    // Find complaints submitted by a particular user
    List<Complaint> findBySubmittedById(Long userId);

    // Find complaints by status
    List<Complaint> findByStatus(ComplaintStatus status);

    // Find complaints assigned to a department
    List<Complaint> findByDepartmentId(Long departmentId);

    // Count complaints by status
    long countByStatus(ComplaintStatus status);
}
