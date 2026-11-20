package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.enums.ComplaintPriority;
import jakarta.validation.constraints.NotNull;

public class PriorityUpdateRequest {

    @NotNull
    private ComplaintPriority priority;

    public ComplaintPriority getPriority() {
        return priority;
    }

    public void setPriority(ComplaintPriority priority) {
        this.priority = priority;
    }
}
