package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.enums.ComplaintStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StatusUpdateRequest {

    @NotNull
    private ComplaintStatus status;

    @Size(max = 500)
    private String remarks;

    private Long updatedByUserId;

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Long updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }
}
