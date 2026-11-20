package com.campusfix.CampusFix.dto;

import java.util.List;
import java.util.Map;

public class DashboardResponse {

    private long totalComplaints;
    private long submittedComplaints;
    private long assignedComplaints;
    private long inProgressComplaints;
    private long resolvedComplaints;
    private long rejectedComplaints;
    private long closedComplaints;

    private long highPriorityComplaints;
    private long urgentPriorityComplaints;

    private long totalUsers;
    private long totalMaintenanceStaff;
    private long totalFeedback;

    private double averageRating;

    private List<DepartmentComplaintSummary> departmentStatistics;
    private Map<String, Long> priorityStatistics;

    public DashboardResponse(
            long totalComplaints,
            long submittedComplaints,
            long assignedComplaints,
            long inProgressComplaints,
            long resolvedComplaints,
            long rejectedComplaints,
            long closedComplaints,
            long highPriorityComplaints,
            long urgentPriorityComplaints,
            long totalUsers,
            long totalMaintenanceStaff,
            long totalFeedback,
            double averageRating,
            List<DepartmentComplaintSummary> departmentStatistics,
            Map<String, Long> priorityStatistics) {

        this.totalComplaints = totalComplaints;
        this.submittedComplaints = submittedComplaints;
        this.assignedComplaints = assignedComplaints;
        this.inProgressComplaints = inProgressComplaints;
        this.resolvedComplaints = resolvedComplaints;
        this.rejectedComplaints = rejectedComplaints;
        this.closedComplaints = closedComplaints;
        this.highPriorityComplaints = highPriorityComplaints;
        this.urgentPriorityComplaints = urgentPriorityComplaints;
        this.totalUsers = totalUsers;
        this.totalMaintenanceStaff = totalMaintenanceStaff;
        this.totalFeedback = totalFeedback;
        this.averageRating = averageRating;
        this.departmentStatistics = departmentStatistics;
        this.priorityStatistics = priorityStatistics;
    }

    public long getTotalComplaints() { return totalComplaints; }
    public long getSubmittedComplaints() { return submittedComplaints; }
    public long getAssignedComplaints() { return assignedComplaints; }
    public long getInProgressComplaints() { return inProgressComplaints; }
    public long getResolvedComplaints() { return resolvedComplaints; }
    public long getRejectedComplaints() { return rejectedComplaints; }
    public long getClosedComplaints() { return closedComplaints; }
    public long getHighPriorityComplaints() { return highPriorityComplaints; }
    public long getUrgentPriorityComplaints() { return urgentPriorityComplaints; }
    public long getTotalUsers() { return totalUsers; }
    public long getTotalMaintenanceStaff() { return totalMaintenanceStaff; }
    public long getTotalFeedback() { return totalFeedback; }
    public double getAverageRating() { return averageRating; }

    public List<DepartmentComplaintSummary> getDepartmentStatistics() {
        return departmentStatistics;
    }

    public Map<String, Long> getPriorityStatistics() {
        return priorityStatistics;
    }
}
