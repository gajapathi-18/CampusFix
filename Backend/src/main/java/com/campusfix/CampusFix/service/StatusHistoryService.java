package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.dto.StatusUpdateRequest;
import com.campusfix.CampusFix.dto.StatusHistoryResponse;
import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.entity.StatusHistory;
import com.campusfix.CampusFix.entity.User;
import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.repository.ComplaintRepository;
import com.campusfix.CampusFix.repository.StatusHistoryRepository;
import com.campusfix.CampusFix.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusHistoryService {

    private final ComplaintRepository complaintRepository;
    private final StatusHistoryRepository historyRepository;
    private final UserRepository userRepository;

    public StatusHistoryService(
            ComplaintRepository complaintRepository,
            StatusHistoryRepository historyRepository,
            UserRepository userRepository) {

        this.complaintRepository = complaintRepository;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public StatusHistoryResponse updateStatus(
            Long complaintId,
            StatusUpdateRequest request) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        ComplaintStatus oldStatus = complaint.getStatus();
        ComplaintStatus newStatus = request.getStatus();

        if (oldStatus == newStatus) {
            throw new IllegalArgumentException(
                    "Complaint already has this status");
        }

        User updatedBy = null;

        if (request.getUpdatedByUserId() != null) {
            updatedBy = userRepository.findById(
                    request.getUpdatedByUserId())
                    .orElseThrow(() ->
                            new RuntimeException("Updating user not found"));
        }

        complaint.setStatus(newStatus);
        complaintRepository.save(complaint);

        StatusHistory history = new StatusHistory();

        history.setComplaint(complaint);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setRemarks(request.getRemarks());
        history.setChangedBy(updatedBy);

        StatusHistory savedHistory =
                historyRepository.save(history);

        return new StatusHistoryResponse(savedHistory);
    }

    @Transactional(readOnly = true)
    public List<StatusHistoryResponse> getComplaintHistory(
            Long complaintId) {

        if (!complaintRepository.existsById(complaintId)) {
            throw new RuntimeException("Complaint not found");
        }

        return historyRepository
                .findByComplaintIdOrderByChangedAtDesc(complaintId)
                .stream()
                .map(StatusHistoryResponse::new)
                .toList();
    }
}
