
package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.entity.Department;
import com.campusfix.CampusFix.repository.DepartmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Add a new department
    public Department addDepartment(String name, String description) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name is required");
        }

        name = name.trim();

        if (departmentRepository.existsByName(name)) {
            throw new IllegalArgumentException("Department already exists");
        }

        Department department = new Department(name, description);

        return departmentRepository.save(department);
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get department by ID
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Department not found with ID: " + id)
                );
    }
}
