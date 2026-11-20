package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.dto.ComplaintResponse;
import com.campusfix.CampusFix.dto.MaintenanceDashboardResponse;

import com.campusfix.CampusFix.entity.Assignment;
import com.campusfix.CampusFix.entity.User;

import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.enums.UserRole;

import com.campusfix.CampusFix.repository.AssignmentRepository;
import com.campusfix.CampusFix.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class MaintenanceDashboardService {

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public MaintenanceDashboardService(
            AssignmentRepository assignmentRepository,
            UserRepository userRepository) {

        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public MaintenanceDashboardResponse getDashboard(Long staffId) {

        User staff = userRepository.findById(staffId)
                .orElseThrow(() ->
                        new RuntimeException("Staff member not found"));

        if (!staff.isActive()
                || staff.getRole() != UserRole.MAINTENANCE) {
            throw new IllegalArgumentException(
                    "User is not an active maintenance staff member");
        }

        List<Assignment> allAssignments =
                assignmentRepository.findAllByOrderByAssignedAtDesc();

        Set<Long> processedComplaintIds = new HashSet<>();

        List<Assignment> currentAssignments = allAssignments.stream()
                .filter(assignment -> {
                    Long complaintId =
                            assignment.getComplaint().getId();

                    return processedComplaintIds.add(complaintId);
                })
                .filter(assignment ->
                        assignment.getMaintenanceUser().getId()
                                .equals(staffId))
                .toList();

        List<ComplaintResponse> tasks = currentAssignments.stream()
                .map(assignment ->
                        new ComplaintResponse(assignment.getComplaint()))
                .toList();

        long assigned = tasks.stream()
                .filter(task ->
                        task.getStatus() == ComplaintStatus.ASSIGNED)
                .count();

        long inProgress = tasks.stream()
                .filter(task ->
                        task.getStatus() == ComplaintStatus.IN_PROGRESS)
                .count();

        long resolved = tasks.stream()
                .filter(task ->
                        task.getStatus() == ComplaintStatus.RESOLVED)
                .count();

        return new MaintenanceDashboardResponse(
                staff.getId(),
                staff.getName(),
                tasks.size(),
                assigned,
                inProgress,
                resolved,
                tasks
        );
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getAssignedTasks(Long staffId) {

        return getDashboard(staffId).getTasks();
    }
}
