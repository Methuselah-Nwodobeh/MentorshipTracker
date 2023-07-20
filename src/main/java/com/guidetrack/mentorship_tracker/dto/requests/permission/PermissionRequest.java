package com.guidetrack.mentorship_tracker.dto.requests.permission;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(
        @NotBlank String name,
        @NotBlank String description
) {
}
