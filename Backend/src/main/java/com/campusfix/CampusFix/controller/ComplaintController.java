package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.ComplaintRequest;
import com.campusfix.CampusFix.dto.ComplaintResponse;
import com.campusfix.CampusFix.service.ComplaintService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:5173")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComplaintResponse submitComplaint(
            @Valid @RequestBody ComplaintRequest request) {

        return complaintService.submitComplaint(request);
    }

    @GetMapping
    public List<ComplaintResponse> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/user/{userId}")
    public List<ComplaintResponse> getComplaintsByUser(
            @PathVariable Long userId) {

        return complaintService.getComplaintsByUser(userId);
    }

    @GetMapping("/{id}")
    public ComplaintResponse getComplaintById(
            @PathVariable Long id) {

        return complaintService.getComplaintById(id);
    }
}
