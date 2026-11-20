package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.enums.ComplaintPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ComplaintRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotBlank
    @Size(max = 100)
    private String category;

    @NotBlank
    @Size(max = 200)
    private String location;

    private ComplaintPriority priority;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ComplaintPriority getPriority() {
        return priority;
    }

    public void setPriority(ComplaintPriority priority) {
        this.priority = priority;
    }
}
