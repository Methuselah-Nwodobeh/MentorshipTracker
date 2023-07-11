package com.guidetrack.mentorship_tracker.controllers;


import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.dto.responses.SignUpResponse;
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
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    @Operation(summary = "Create an admin")
    public SignUpResponse register(@Valid @RequestBody AdminSignupRequest request) {
        log.info("this is request {}", request.toString());
        return adminService.registerAdmin(request);
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Login an Admin")
    public JwtAuthenticationResponse login(@Valid @RequestBody LoginRequest request) {
        log.info("this is request");
            return adminService.signInAdmin(request);
    }
}
