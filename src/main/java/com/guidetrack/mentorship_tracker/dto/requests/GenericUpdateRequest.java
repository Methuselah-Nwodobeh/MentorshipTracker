package com.guidetrack.mentorship_tracker.dto.requests;

import jakarta.validation.constraints.NotNull;

public record GenericUpdateRequest<T, G>(
        @NotNull T identifier,
        @NotNull G replacement
) {
}
