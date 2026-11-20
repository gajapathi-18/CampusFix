package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.ComplaintResponse;
import com.campusfix.CampusFix.dto.PriorityUpdateRequest;
import com.campusfix.CampusFix.enums.ComplaintPriority;
import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.service.AdminComplaintService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/complaints")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminComplaintController {

    private final AdminComplaintService adminComplaintService;

    public AdminComplaintController(
            AdminComplaintService adminComplaintService) {
        this.adminComplaintService = adminComplaintService;
    }

    @GetMapping
    public List<ComplaintResponse> getFilteredComplaints(
            @RequestParam(required = false) ComplaintStatus status,
            @RequestParam(required = false) ComplaintPriority priority,
            @RequestParam(required = false) Long departmentId) {

        return adminComplaintService.getFilteredComplaints(
                status, priority, departmentId);
    }

    @GetMapping("/unassigned")
    public List<ComplaintResponse> getUnassignedComplaints() {

        return adminComplaintService.getUnassignedComplaints();
    }

    @GetMapping("/{complaintId}")
    public ComplaintResponse getComplaintDetails(
            @PathVariable Long complaintId) {

        return adminComplaintService.getComplaintDetails(complaintId);
    }

    @PatchMapping("/{complaintId}/priority")
    public ComplaintResponse updatePriority(
            @PathVariable Long complaintId,
            @Valid @RequestBody PriorityUpdateRequest request) {

        return adminComplaintService.updatePriority(
                complaintId, request.getPriority());
    }
}
