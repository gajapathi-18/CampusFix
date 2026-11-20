package com.campusfix.CampusFix.dto;

public class DepartmentComplaintSummary {

    private String departmentName;
    private long totalComplaints;

    public DepartmentComplaintSummary(
            String departmentName, long totalComplaints) {
        this.departmentName = departmentName;
        this.totalComplaints = totalComplaints;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public long getTotalComplaints() {
        return totalComplaints;
    }
}
