package com.guidetrack.mentorship_tracker.controllers;

import com.guidetrack.mentorship_tracker.dto.requests.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.services.BasicUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/")
@Slf4j
public class UserController {
    @Qualifier("basicUserServiceImpl")
    private final BasicUserService basicUserService;
    @PostMapping(path = "/login")
    @Operation(summary = "login")
    public JwtAuthenticationResponse login(@Valid @RequestBody LoginRequest request) {
            return basicUserService.login(request);
    }
}
