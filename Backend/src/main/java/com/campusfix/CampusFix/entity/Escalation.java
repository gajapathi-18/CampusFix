package com.campusfix.CampusFix.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "escalations")
public class Escalation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false, unique = true)
    private Complaint complaint;

    @Column(nullable = false, length = 500)
    private String message;

    private LocalDateTime escalatedAt;

    public Escalation() {
    }

    @PrePersist
    protected void onCreate() {
        escalatedAt = LocalDateTime.now();
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getEscalatedAt() {
        return escalatedAt;
    }
}
