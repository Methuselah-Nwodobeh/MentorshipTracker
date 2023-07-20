package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dto.requests.role.RoleRequest;
import com.guidetrack.mentorship_tracker.dto.requests.role.UpdateRoleRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface RoleService {
    ResponseEntity<DefaultResponse> create(@Valid RoleRequest request);

    ResponseEntity<DefaultResponse> update(@Valid UpdateRoleRequest request);

    ResponseEntity<DefaultResponse> delete(@NotBlank @RequestBody UUID uuid);

    ResponseEntity<DefaultResponse> read(@NotBlank @RequestBody UUID uuid);

    ResponseEntity<DefaultResponse> readAll();
}
