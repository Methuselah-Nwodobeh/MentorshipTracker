package com.guidetrack.mentorship_tracker.dto.requests.role;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record UpdateRoleRequest(
        @NotNull UUID uuid,
        String name,
        String description,
        Set<String> permissions
) {
}
