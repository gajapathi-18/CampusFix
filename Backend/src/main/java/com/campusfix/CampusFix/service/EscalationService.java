package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.entity.Escalation;
import com.campusfix.CampusFix.enums.ComplaintPriority;
import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.repository.ComplaintRepository;
import com.campusfix.CampusFix.repository.EscalationRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EscalationService {

    private final ComplaintRepository complaintRepository;
    private final EscalationRepository escalationRepository;

    @Value("${campusfix.escalation.hours:24}")
    private long escalationHours;

    public EscalationService(
            ComplaintRepository complaintRepository,
            EscalationRepository escalationRepository) {

        this.complaintRepository = complaintRepository;
        this.escalationRepository = escalationRepository;
    }

    @Transactional
    public void checkAndEscalateComplaints() {

        LocalDateTime deadline =
                LocalDateTime.now().minusHours(escalationHours);

        List<Complaint> complaints = complaintRepository.findAll();

        for (Complaint complaint : complaints) {

            boolean activeStatus =
                    complaint.getStatus() != ComplaintStatus.RESOLVED
                    && complaint.getStatus() != ComplaintStatus.CLOSED
                    && complaint.getStatus() != ComplaintStatus.REJECTED;

            boolean highPriority =
                    complaint.getPriority() == ComplaintPriority.HIGH
                    || complaint.getPriority() == ComplaintPriority.URGENT;

            boolean overdue =
                    complaint.getCreatedAt() != null
                    && complaint.getCreatedAt().isBefore(deadline);

            boolean alreadyEscalated =
                    escalationRepository.existsByComplaintId(
                            complaint.getId());

            if (activeStatus && highPriority
                    && overdue && !alreadyEscalated) {

                if (complaint.getPriority() == ComplaintPriority.HIGH) {
                    complaint.setPriority(ComplaintPriority.URGENT);
                    complaintRepository.save(complaint);
                }

                Escalation escalation = new Escalation();

                escalation.setComplaint(complaint);
                escalation.setMessage(
                        "Complaint unresolved for "
                        + escalationHours
                        + " hours. Escalated for immediate attention.");

                escalationRepository.save(escalation);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<java.util.Map<String, Object>> getAllEscalations() {

        return escalationRepository
                .findAllByOrderByEscalatedAtDesc()
                .stream()
                .map(escalation -> {
                    Complaint complaint = escalation.getComplaint();

                    java.util.Map<String, Object> result =
                            new java.util.LinkedHashMap<>();

                    result.put("escalationId", escalation.getId());
                    result.put("complaintId", complaint.getId());
                    result.put("complaintTitle", complaint.getTitle());
                    result.put("priority", complaint.getPriority());
                    result.put("status", complaint.getStatus());
                    result.put("message", escalation.getMessage());
                    result.put("escalatedAt", escalation.getEscalatedAt());

                    return result;
                })
                .toList();
    }
}
