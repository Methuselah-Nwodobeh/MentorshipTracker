package com.guidetrack.mentorship_tracker.dto.requests;

import jakarta.validation.constraints.NotBlank;

import java.lang.reflect.Array;

public record SignupRequest (
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String dateOfBirth,
        @NotBlank Array location,
        @NotBlank String role
){
}
