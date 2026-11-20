package com.campusfix.CampusFix.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_user_id", nullable = false)
    private User maintenanceUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    private User assignedBy;

    private LocalDateTime assignedAt;

    public Assignment() {
    }

    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public User getMaintenanceUser() {
        return maintenanceUser;
    }

    public void setMaintenanceUser(User maintenanceUser) {
        this.maintenanceUser = maintenanceUser;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
}
