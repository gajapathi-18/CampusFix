package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.entity.Assignment;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AssignmentDTO {

    @NotNull
    private Long maintenanceUserId;

    @NotNull
    private Long assignedByUserId;

    private Long id;
    private Long complaintId;
    private String maintenanceUserName;
    private String assignedByName;
    private LocalDateTime assignedAt;

    public AssignmentDTO() {
    }

    public AssignmentDTO(Assignment assignment) {

        this.id = assignment.getId();
        this.complaintId = assignment.getComplaint().getId();

        this.maintenanceUserId =
                assignment.getMaintenanceUser().getId();

        this.maintenanceUserName =
                assignment.getMaintenanceUser().getName();

        this.assignedByUserId =
                assignment.getAssignedBy().getId();

        this.assignedByName =
                assignment.getAssignedBy().getName();

        this.assignedAt = assignment.getAssignedAt();
    }

    public Long getMaintenanceUserId() {
        return maintenanceUserId;
    }

    public void setMaintenanceUserId(Long maintenanceUserId) {
        this.maintenanceUserId = maintenanceUserId;
    }

    public Long getAssignedByUserId() {
        return assignedByUserId;
    }

    public void setAssignedByUserId(Long assignedByUserId) {
        this.assignedByUserId = assignedByUserId;
    }

    public Long getId() {
        return id;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public String getMaintenanceUserName() {
        return maintenanceUserName;
    }

    public String getAssignedByName() {
        return assignedByName;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
}
