package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.FeedbackDTO;
import com.campusfix.CampusFix.service.FeedbackService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/{complaintId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDTO submitFeedback(
            @PathVariable Long complaintId,
            @Valid @RequestBody FeedbackDTO request) {

        return feedbackService.submitFeedback(complaintId, request);
    }

    @GetMapping
    public List<FeedbackDTO> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/user/{userId}")
    public List<FeedbackDTO> getFeedbackByUser(
            @PathVariable Long userId) {

        return feedbackService.getFeedbackByUser(userId);
    }
}
