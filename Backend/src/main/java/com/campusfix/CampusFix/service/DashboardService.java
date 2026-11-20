package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.dto.DashboardResponse;
import com.campusfix.CampusFix.dto.DepartmentComplaintSummary;

import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.entity.Feedback;
import com.campusfix.CampusFix.entity.User;

import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.enums.ComplaintPriority;
import com.campusfix.CampusFix.enums.UserRole;

import com.campusfix.CampusFix.repository.ComplaintRepository;
import com.campusfix.CampusFix.repository.FeedbackRepository;
import com.campusfix.CampusFix.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    public DashboardService(
            ComplaintRepository complaintRepository,
            UserRepository userRepository,
            FeedbackRepository feedbackRepository) {

        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getDashboardStatistics() {

        List<Complaint> complaints = complaintRepository.findAll();
        List<User> users = userRepository.findAll();
        List<Feedback> feedbacks = feedbackRepository.findAll();

        long totalComplaints = complaints.size();

        long submitted = countByStatus(
                complaints, ComplaintStatus.SUBMITTED);

        long assigned = countByStatus(
                complaints, ComplaintStatus.ASSIGNED);

        long inProgress = countByStatus(
                complaints, ComplaintStatus.IN_PROGRESS);

        long resolved = countByStatus(
                complaints, ComplaintStatus.RESOLVED);

        long rejected = countByStatus(
                complaints, ComplaintStatus.REJECTED);

        long closed = countByStatus(
                complaints, ComplaintStatus.CLOSED);

        long highPriority = complaints.stream()
                .filter(c -> c.getPriority() == ComplaintPriority.HIGH)
                .count();

        long urgentPriority = complaints.stream()
                .filter(c -> c.getPriority() == ComplaintPriority.URGENT)
                .count();

        long totalUsers = users.stream()
                .filter(User::isActive)
                .count();

        long maintenanceStaff = users.stream()
                .filter(User::isActive)
                .filter(u -> u.getRole() == UserRole.MAINTENANCE)
                .count();

        long totalFeedback = feedbacks.size();

        double averageRating = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        averageRating = Math.round(averageRating * 100.0) / 100.0;

        Map<String, Long> departmentCounts = complaints.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getDepartment() == null
                                ? "Unassigned"
                                : c.getDepartment().getName(),
                        Collectors.counting()
                ));

        List<DepartmentComplaintSummary> departmentStatistics =
                departmentCounts.entrySet().stream()
                        .map(entry -> new DepartmentComplaintSummary(
                                entry.getKey(), entry.getValue()))
                        .toList();

        Map<String, Long> priorityStatistics = new HashMap<>();

        for (ComplaintPriority priority : ComplaintPriority.values()) {
            long count = complaints.stream()
                    .filter(c -> c.getPriority() == priority)
                    .count();

            priorityStatistics.put(priority.name(), count);
        }

        return new DashboardResponse(
                totalComplaints,
                submitted,
                assigned,
                inProgress,
                resolved,
                rejected,
                closed,
                highPriority,
                urgentPriority,
                totalUsers,
                maintenanceStaff,
                totalFeedback,
                averageRating,
                departmentStatistics,
                priorityStatistics
        );
    }

    private long countByStatus(
            List<Complaint> complaints,
            ComplaintStatus status) {

        return complaints.stream()
                .filter(c -> c.getStatus() == status)
                .count();
    }
}
