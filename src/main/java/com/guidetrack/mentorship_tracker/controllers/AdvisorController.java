package com.guidetrack.mentorship_tracker.controllers;

import com.guidetrack.mentorship_tracker.dto.requests.user.SignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.services.BasicUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/advisor")
@Slf4j
public class AdvisorController {
    private final BasicUserService basicUserService;

     @PostMapping()
    @Operation(summary = "Create an advisor")
    public ResponseEntity<DefaultResponse> register(@Valid @RequestBody SignupRequest request) {
        log.info("this is advisor request {}", request.toString());
        return basicUserService.register(request);
    }
}
