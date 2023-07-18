package com.guidetrack.mentorship_tracker.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import com.guidetrack.mentorship_tracker.dto.requests.role.RoleRequest;
import com.guidetrack.mentorship_tracker.dto.requests.role.UpdateRoleRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.services.RoleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role/")
@Slf4j
public class RoleController {
    private final RoleService roleService;

    @PostMapping()
    @Operation(summary = "create a role")
    public ResponseEntity<DefaultResponse> create(@Valid @RequestBody RoleRequest request) {
        return roleService.create(request);
    }

    @PutMapping()
    @Operation(summary = "update the fields of a role")
    public ResponseEntity<DefaultResponse> update(@Valid @RequestBody UpdateRoleRequest request) {
        return roleService.update(request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a role")
    public ResponseEntity<DefaultResponse> delete(@NotBlank @PathVariable UUID id) {
        return roleService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "read a role")
    public ResponseEntity<DefaultResponse> read(@NotBlank @PathVariable UUID id) {
        return roleService.read(id);
    }

    @GetMapping()
    @Operation(summary = "read all roles in the database")
    public ResponseEntity<DefaultResponse> readAll() {
        return roleService.readAll();
    }

}
