package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.dto.FeedbackDTO;
import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.entity.Feedback;
import com.campusfix.CampusFix.entity.User;
import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.repository.ComplaintRepository;
import com.campusfix.CampusFix.repository.FeedbackRepository;
import com.campusfix.CampusFix.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            ComplaintRepository complaintRepository,
            UserRepository userRepository) {

        this.feedbackRepository = feedbackRepository;
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FeedbackDTO submitFeedback(
            Long complaintId, FeedbackDTO request) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new IllegalArgumentException("User account is inactive");
        }

        if (!complaint.getSubmittedBy().getId().equals(user.getId())) {
            throw new IllegalArgumentException(
                    "Only the student who submitted this complaint can rate it");
        }

        if (complaint.getStatus() != ComplaintStatus.RESOLVED) {
            throw new IllegalArgumentException(
                    "Feedback is allowed only for resolved complaints");
        }

        if (feedbackRepository.existsByComplaintId(complaintId)) {
            throw new IllegalArgumentException(
                    "Feedback already submitted for this complaint");
        }

        if (request.getRating() == null
                || request.getRating() < 1
                || request.getRating() > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5");
        }

        Feedback feedback = new Feedback();

        feedback.setComplaint(complaint);
        feedback.setSubmittedBy(user);
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());

        Feedback savedFeedback = feedbackRepository.save(feedback);

        return new FeedbackDTO(savedFeedback);
    }

    @Transactional(readOnly = true)
    public List<FeedbackDTO> getAllFeedback() {

        return feedbackRepository.findAll()
                .stream()
                .map(FeedbackDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FeedbackDTO> getFeedbackByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        return feedbackRepository.findBySubmittedById(userId)
                .stream()
                .map(FeedbackDTO::new)
                .toList();
    }
}
