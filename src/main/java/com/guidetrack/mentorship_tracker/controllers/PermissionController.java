package com.guidetrack.mentorship_tracker.controllers;

import com.guidetrack.mentorship_tracker.dto.requests.permission.PermissionRequest;
import com.guidetrack.mentorship_tracker.dto.requests.permission.UpdatePermissionRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.services.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permission/")
@Slf4j
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping()
    @Operation(summary = "create a role")
    public ResponseEntity<DefaultResponse> create(@Valid @RequestBody PermissionRequest request){
        return permissionService.create(request);
    }

    @PutMapping()
    @Operation(summary = "update the fields of a permission")
    public ResponseEntity<DefaultResponse> update(@Valid @RequestBody UpdatePermissionRequest request){
        return permissionService.update(request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a permission")
    public ResponseEntity<DefaultResponse> delete(@NotBlank @PathVariable UUID id){
        return permissionService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "read a permission")
    public ResponseEntity<DefaultResponse> read(@NotBlank @PathVariable UUID id){
        return permissionService.read(id);
    }

    @GetMapping()
    @Operation(summary = "read all permissions in the database")
    public ResponseEntity<DefaultResponse> readAll(){
        return permissionService.readAll();
    }

}
