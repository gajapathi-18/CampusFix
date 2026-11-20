package com.campusfix.CampusFix.dto;

import java.util.List;

public class MaintenanceDashboardResponse {

    private Long staffId;
    private String staffName;

    private long totalTasks;
    private long assignedTasks;
    private long inProgressTasks;
    private long resolvedTasks;

    private List<ComplaintResponse> tasks;

    public MaintenanceDashboardResponse(
            Long staffId,
            String staffName,
            long totalTasks,
            long assignedTasks,
            long inProgressTasks,
            long resolvedTasks,
            List<ComplaintResponse> tasks) {

        this.staffId = staffId;
        this.staffName = staffName;
        this.totalTasks = totalTasks;
        this.assignedTasks = assignedTasks;
        this.inProgressTasks = inProgressTasks;
        this.resolvedTasks = resolvedTasks;
        this.tasks = tasks;
    }

    public Long getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public long getAssignedTasks() {
        return assignedTasks;
    }

    public long getInProgressTasks() {
        return inProgressTasks;
    }

    public long getResolvedTasks() {
        return resolvedTasks;
    }

    public List<ComplaintResponse> getTasks() {
        return tasks;
    }
}
