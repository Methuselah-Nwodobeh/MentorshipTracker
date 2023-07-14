package com.guidetrack.mentorship_tracker.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record AdminSignupRequest(
        @NotBlank String username,
        @NotBlank String firstname,
        @NotBlank String email,
        @NotBlank String password) {
}
