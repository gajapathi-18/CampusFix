package com.campusfix.CampusFix.controller;

import com.campusfix.CampusFix.dto.UserRegistrationRequest;
import com.campusfix.CampusFix.dto.UserResponse;
import com.campusfix.CampusFix.dto.LoginRequest;
import com.campusfix.CampusFix.entity.User;
import com.campusfix.CampusFix.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(
            @RequestBody UserRegistrationRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        User savedUser = userService.registerUser(
                user,
                request.getDepartmentId()
        );

        return new UserResponse(savedUser);
    }

    @PostMapping("/login")
    public UserResponse loginUser(@RequestBody LoginRequest request) {

        User user = userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );

        return new UserResponse(user);
    }
}
