package com.guidetrack.mentorship_tracker.dto.requests.role;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank String name,
        @NotBlank String description
) {
}
