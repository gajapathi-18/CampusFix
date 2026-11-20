package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.entity.Feedback;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class FeedbackDTO {

    @NotNull
    private Long userId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @Size(max = 1000)
    private String comment;

    private Long id;
    private Long complaintId;
    private String studentName;
    private LocalDateTime createdAt;

    public FeedbackDTO() {
    }

    public FeedbackDTO(Feedback feedback) {
        this.id = feedback.getId();
        this.complaintId = feedback.getComplaint().getId();
        this.userId = feedback.getSubmittedBy().getId();
        this.studentName = feedback.getSubmittedBy().getName();
        this.rating = feedback.getRating();
        this.comment = feedback.getComment();
        this.createdAt = feedback.getCreatedAt();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public String getStudentName() {
        return studentName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
