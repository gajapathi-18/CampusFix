package com.campusfix.CampusFix.dto;

import com.campusfix.CampusFix.entity.User;
import com.campusfix.CampusFix.entity.Department;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String departmentName;
    private boolean active;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole().name();
        this.active = user.isActive();

        Department department = user.getDepartment();

        if (department != null) {
            this.departmentName = department.getName();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public boolean isActive() {
        return active;
    }
}
