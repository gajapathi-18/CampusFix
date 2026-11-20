package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.StatusUpdateRequest;
import com.campusfix.CampusFix.dto.StatusHistoryResponse;
import com.campusfix.CampusFix.service.StatusHistoryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:5173")
public class StatusHistoryController {

    private final StatusHistoryService statusHistoryService;

    public StatusHistoryController(
            StatusHistoryService statusHistoryService) {
        this.statusHistoryService = statusHistoryService;
    }

    @PatchMapping("/{complaintId}/status")
    public StatusHistoryResponse updateStatus(
            @PathVariable Long complaintId,
            @Valid @RequestBody StatusUpdateRequest request) {

        return statusHistoryService.updateStatus(
                complaintId, request);
    }

    @GetMapping("/{complaintId}/history")
    public List<StatusHistoryResponse> getComplaintHistory(
            @PathVariable Long complaintId) {

        return statusHistoryService.getComplaintHistory(complaintId);
    }
}
