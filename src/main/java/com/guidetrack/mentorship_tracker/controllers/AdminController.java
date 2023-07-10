package com.guidetrack.mentorship_tracker.controllers;


import com.guidetrack.mentorship_tracker.dao.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dao.requests.LoginRequest;
import com.guidetrack.mentorship_tracker.dao.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.dao.responses.SignUpResponse;
import com.guidetrack.mentorship_tracker.exceptions.BadRequestException;
import com.guidetrack.mentorship_tracker.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    @Operation(summary = "Create an admin")
    public SignUpResponse register(@Valid @RequestBody AdminSignupRequest request) {
        log.info("this is request {}", request.toString());

        SignUpResponse registeredAdmin;
        try {
            registeredAdmin = adminService.registerAdmin(request);
        } catch (BadRequestException exception) {
            return new SignUpResponse("error", exception.getMessage());
        }
        return registeredAdmin;
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Login an Admin")
    public JwtAuthenticationResponse login(@Valid @RequestBody LoginRequest request) {
        JwtAuthenticationResponse jwtAuthenticationResponse;
        try {
            jwtAuthenticationResponse = adminService.signInAdmin(request);
        } catch (BadRequestException exception) {
            return new JwtAuthenticationResponse("error", exception.getMessage(), exception.getMessage());
        }
        return jwtAuthenticationResponse;
    }
}
