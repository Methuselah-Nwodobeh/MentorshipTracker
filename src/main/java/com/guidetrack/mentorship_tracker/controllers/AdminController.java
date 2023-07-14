package com.guidetrack.mentorship_tracker.controllers;


import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
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
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminController {

    @Qualifier("adminUserServiceImpl")
    private final BasicUserService basicUserService;

    @PostMapping("/register")
    @Operation(summary = "Create an admin")
    public DefaultResponse register(@Valid @RequestBody AdminSignupRequest request) {
        log.info("this is admin request {}", request.toString());
        return basicUserService.register(request);
    }


}
