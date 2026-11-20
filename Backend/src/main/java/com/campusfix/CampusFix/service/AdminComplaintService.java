package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.dto.ComplaintResponse;
import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.enums.ComplaintPriority;
import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.repository.AssignmentRepository;
import com.campusfix.CampusFix.repository.ComplaintRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminComplaintService {

    private final ComplaintRepository complaintRepository;
    private final AssignmentRepository assignmentRepository;

    public AdminComplaintService(
            ComplaintRepository complaintRepository,
            AssignmentRepository assignmentRepository) {

        this.complaintRepository = complaintRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getFilteredComplaints(
            ComplaintStatus status,
            ComplaintPriority priority,
            Long departmentId) {

        return complaintRepository.findAll()
                .stream()
                .filter(c -> status == null || c.getStatus() == status)
                .filter(c -> priority == null || c.getPriority() == priority)
                .filter(c -> departmentId == null
                        || (c.getDepartment() != null
                        && c.getDepartment().getId().equals(departmentId)))
                .map(ComplaintResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getUnassignedComplaints() {

        return complaintRepository.findAll()
                .stream()
                .filter(c -> !assignmentRepository.existsByComplaintId(
                        c.getId()))
                .filter(c -> c.getStatus() != ComplaintStatus.RESOLVED
                        && c.getStatus() != ComplaintStatus.CLOSED
                        && c.getStatus() != ComplaintStatus.REJECTED)
                .map(ComplaintResponse::new)
                .toList();
    }

    @Transactional
    public ComplaintResponse updatePriority(
            Long complaintId,
            ComplaintPriority priority) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        if (complaint.getStatus() == ComplaintStatus.RESOLVED
                || complaint.getStatus() == ComplaintStatus.CLOSED
                || complaint.getStatus() == ComplaintStatus.REJECTED) {
            throw new IllegalArgumentException(
                    "Cannot change priority of a completed complaint");
        }

        complaint.setPriority(priority);

        Complaint updated = complaintRepository.save(complaint);

        return new ComplaintResponse(updated);
    }

    @Transactional(readOnly = true)
    public ComplaintResponse getComplaintDetails(Long complaintId) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        return new ComplaintResponse(complaint);
    }
}
