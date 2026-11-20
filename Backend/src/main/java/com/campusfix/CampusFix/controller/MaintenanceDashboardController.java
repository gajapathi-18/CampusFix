package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.ComplaintResponse;
import com.campusfix.CampusFix.dto.MaintenanceDashboardResponse;
import com.campusfix.CampusFix.service.MaintenanceDashboardService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "http://localhost:5173")
public class MaintenanceDashboardController {

    private final MaintenanceDashboardService maintenanceDashboardService;

    public MaintenanceDashboardController(
            MaintenanceDashboardService maintenanceDashboardService) {

        this.maintenanceDashboardService =
                maintenanceDashboardService;
    }

    @GetMapping("/{staffId}/dashboard")
    public MaintenanceDashboardResponse getDashboard(
            @PathVariable Long staffId) {

        return maintenanceDashboardService.getDashboard(staffId);
    }

    @GetMapping("/{staffId}/tasks")
    public List<ComplaintResponse> getAssignedTasks(
            @PathVariable Long staffId) {

        return maintenanceDashboardService.getAssignedTasks(staffId);
    }
}
