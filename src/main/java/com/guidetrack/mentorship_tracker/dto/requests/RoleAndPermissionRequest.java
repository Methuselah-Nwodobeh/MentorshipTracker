package com.guidetrack.mentorship_tracker.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record RoleAndPermissionRequest(
        @NotBlank String name,
        @NotBlank String description
) {
}
