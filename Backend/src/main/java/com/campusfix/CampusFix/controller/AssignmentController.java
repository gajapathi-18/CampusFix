package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.AssignmentDTO;
import com.campusfix.CampusFix.service.AssignmentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:5173")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/{complaintId}/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDTO assignComplaint(
            @PathVariable Long complaintId,
            @Valid @RequestBody AssignmentDTO request) {

        return assignmentService.assignComplaint(
                complaintId, request);
    }

    @GetMapping("/{complaintId}/assignments")
    public List<AssignmentDTO> getComplaintAssignments(
            @PathVariable Long complaintId) {

        return assignmentService.getComplaintAssignments(complaintId);
    }

    @GetMapping("/staff/{userId}/assignments")
    public List<AssignmentDTO> getStaffAssignments(
            @PathVariable Long userId) {

        return assignmentService.getStaffAssignments(userId);
    }
}
