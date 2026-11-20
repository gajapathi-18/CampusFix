
package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.entity.Department;
import com.campusfix.CampusFix.service.DepartmentService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:5173")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Add department
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(
                department.getName(),
                department.getDescription()
        );
    }

    // Get all departments
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    // Get department by ID
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }
}
