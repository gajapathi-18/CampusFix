package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.entity.User;
import com.campusfix.CampusFix.entity.Department;
import com.campusfix.CampusFix.repository.UserRepository;
import com.campusfix.CampusFix.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public User registerUser(User user, Long departmentId) {

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters");
        }

        if (user.getRole() == null) {
            throw new IllegalArgumentException("User role is required");
        }

        String email = user.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        user.setEmail(email);

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign department if provided
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() ->
                            new RuntimeException("Department not found"));

            user.setDepartment(department);
        }

        return userRepository.save(user);
    }

    // Find user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Your account is inactive");
        }

        return user;
    }
}
