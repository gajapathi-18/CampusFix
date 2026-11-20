package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.service.EscalationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escalations")
@CrossOrigin(origins = "http://localhost:5173")
public class EscalationController {

    private final EscalationService escalationService;

    public EscalationController(
            EscalationService escalationService) {
        this.escalationService = escalationService;
    }

    @GetMapping
    public List<java.util.Map<String, Object>> getAllEscalations() {
        return escalationService.getAllEscalations();
    }
}
