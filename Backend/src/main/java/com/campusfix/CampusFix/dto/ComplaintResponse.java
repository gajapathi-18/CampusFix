package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.enums.ComplaintPriority;
import com.campusfix.CampusFix.enums.ComplaintStatus;

import java.time.LocalDateTime;

public class ComplaintResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private ComplaintPriority priority;
    private ComplaintStatus status;
    private Long userId;
    private String userName;
    private String departmentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ComplaintResponse(Complaint complaint) {
        this.id = complaint.getId();
        this.title = complaint.getTitle();
        this.description = complaint.getDescription();
        this.category = complaint.getCategory();
        this.location = complaint.getLocation();
        this.priority = complaint.getPriority();
        this.status = complaint.getStatus();

        if (complaint.getSubmittedBy() != null) {
            this.userId = complaint.getSubmittedBy().getId();
            this.userName = complaint.getSubmittedBy().getName();
        }

        if (complaint.getDepartment() != null) {
            this.departmentName = complaint.getDepartment().getName();
        }

        this.createdAt = complaint.getCreatedAt();
        this.updatedAt = complaint.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public ComplaintPriority getPriority() { return priority; }
    public ComplaintStatus getStatus() { return status; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getDepartmentName() { return departmentName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
