package com.guidetrack.mentorship_tracker.dto.requests.user;

import jakarta.validation.constraints.NotBlank;


public record LoginRequest(
        @NotBlank String email,
        @NotBlank String password) {
}
