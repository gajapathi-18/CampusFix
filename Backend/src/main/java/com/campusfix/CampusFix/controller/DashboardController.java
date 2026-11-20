package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.DashboardResponse;
import com.campusfix.CampusFix.service.DashboardService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin")
    public DashboardResponse getAdminDashboard() {
        return dashboardService.getDashboardStatistics();
    }
}
