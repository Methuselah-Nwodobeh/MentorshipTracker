package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dto.requests.permission.PermissionRequest;
import com.guidetrack.mentorship_tracker.dto.requests.permission.UpdatePermissionRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface PermissionService {
    ResponseEntity<DefaultResponse> create(@Valid PermissionRequest request);

    ResponseEntity<DefaultResponse> update(@Valid UpdatePermissionRequest request);

    ResponseEntity<DefaultResponse> delete(@NotBlank @RequestBody UUID uuid);

    ResponseEntity<DefaultResponse> read(@NotBlank @RequestBody UUID uuid);

    ResponseEntity<DefaultResponse> readAll();
}
