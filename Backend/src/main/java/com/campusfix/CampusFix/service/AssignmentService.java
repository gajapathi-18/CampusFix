package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.dto.AssignmentDTO;
import com.campusfix.CampusFix.entity.Assignment;
import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.entity.User;
import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.enums.UserRole;
import com.campusfix.CampusFix.repository.AssignmentRepository;
import com.campusfix.CampusFix.repository.ComplaintRepository;
import com.campusfix.CampusFix.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public AssignmentService(
            AssignmentRepository assignmentRepository,
            ComplaintRepository complaintRepository,
            UserRepository userRepository) {

        this.assignmentRepository = assignmentRepository;
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AssignmentDTO assignComplaint(
            Long complaintId,
            AssignmentDTO request) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        User maintenanceUser = userRepository.findById(
                request.getMaintenanceUserId())
                .orElseThrow(() ->
                        new RuntimeException("Maintenance user not found"));

        User assignedBy = userRepository.findById(
                request.getAssignedByUserId())
                .orElseThrow(() ->
                        new RuntimeException("Admin user not found"));

        if (!maintenanceUser.isActive()
                || maintenanceUser.getRole() != UserRole.MAINTENANCE) {
            throw new IllegalArgumentException(
                    "Selected user is not an active maintenance staff member");
        }

        if (!assignedBy.isActive()
                || assignedBy.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException(
                    "Only an active admin can assign complaints");
        }

        if (complaint.getStatus() == ComplaintStatus.RESOLVED
                || complaint.getStatus() == ComplaintStatus.CLOSED
                || complaint.getStatus() == ComplaintStatus.REJECTED) {
            throw new IllegalArgumentException(
                    "This complaint cannot be assigned");
        }

        Assignment assignment = new Assignment();

        assignment.setComplaint(complaint);
        assignment.setMaintenanceUser(maintenanceUser);
        assignment.setAssignedBy(assignedBy);

        complaint.setStatus(ComplaintStatus.ASSIGNED);

        complaintRepository.save(complaint);

        Assignment savedAssignment =
                assignmentRepository.save(assignment);

        return new AssignmentDTO(savedAssignment);
    }

    @Transactional(readOnly = true)
    public List<AssignmentDTO> getComplaintAssignments(Long complaintId) {

        if (!complaintRepository.existsById(complaintId)) {
            throw new RuntimeException("Complaint not found");
        }

        return assignmentRepository
                .findByComplaintIdOrderByAssignedAtDesc(complaintId)
                .stream()
                .map(AssignmentDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AssignmentDTO> getStaffAssignments(Long userId) {

        return assignmentRepository
                .findByMaintenanceUserIdOrderByAssignedAtDesc(userId)
                .stream()
                .map(AssignmentDTO::new)
                .toList();
    }
}
