package com.guidetrack.mentorship_tracker.dto.requests.permission;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record UpdatePermissionRequest(
        @NotNull UUID uuid,
        String name,
        String description,
        Set<String> roles
) {
}
