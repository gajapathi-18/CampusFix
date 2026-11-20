package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.entity.StatusHistory;
import com.campusfix.CampusFix.enums.ComplaintStatus;

import java.time.LocalDateTime;

public class StatusHistoryResponse {

    private Long id;
    private Long complaintId;
    private ComplaintStatus oldStatus;
    private ComplaintStatus newStatus;
    private String remarks;
    private String changedByName;
    private LocalDateTime changedAt;

    public StatusHistoryResponse(StatusHistory history) {

        this.id = history.getId();
        this.complaintId = history.getComplaint().getId();
        this.oldStatus = history.getOldStatus();
        this.newStatus = history.getNewStatus();
        this.remarks = history.getRemarks();
        this.changedAt = history.getChangedAt();

        if (history.getChangedBy() != null) {
            this.changedByName = history.getChangedBy().getName();
        }
    }

    public Long getId() { return id; }
    public Long getComplaintId() { return complaintId; }
    public ComplaintStatus getOldStatus() { return oldStatus; }
    public ComplaintStatus getNewStatus() { return newStatus; }
    public String getRemarks() { return remarks; }
    public String getChangedByName() { return changedByName; }
    public LocalDateTime getChangedAt() { return changedAt; }
}
