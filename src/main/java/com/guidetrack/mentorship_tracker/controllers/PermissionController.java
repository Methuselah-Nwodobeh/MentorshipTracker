package com.guidetrack.mentorship_tracker.controllers;

import com.guidetrack.mentorship_tracker.dto.requests.GenericUpdateRequest;
import com.guidetrack.mentorship_tracker.dto.requests.RoleAndPermissionRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.services.RoleAndPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permission/")
@Slf4j
public class PermissionController {
    @Qualifier("permissionServiceImpl")
    private final RoleAndPermissionService permissionService;

    @PostMapping(path = "/create")
    @Operation(summary = "create a role")
    public DefaultResponse create(@Valid @RequestBody RoleAndPermissionRequest request){
        return permissionService.create(request);
    }

    @PostMapping(path = "/update_name")
    @Operation(summary = "update the name of a permission")
    public DefaultResponse updateName(@Valid @RequestBody GenericUpdateRequest<String, String> request){
        return permissionService.updateName(request);
    }

    @PostMapping(path = "/update_description")
    @Operation(summary = "update the description of a permission")
    public DefaultResponse updateDescription(@Valid @RequestBody GenericUpdateRequest<String, String> request){
        return permissionService.updateDescription(request);
    }

    @PostMapping(path = "/delete")
    @Operation(summary = "delete a permission")
    public DefaultResponse delete(@NotBlank @RequestBody String request){
        return permissionService.delete(request);
    }

    @GetMapping(path = "/read")
    @Operation(summary = "read a permission")
    public DefaultResponse read(@NotBlank @RequestBody String request){
        return permissionService.read(request);
    }

    @PostMapping(path = "/read_all")
    @Operation(summary = "read all permissions in the database")
    public DefaultResponse readAll(){
        return permissionService.readAll();
    }

    @PostMapping(path = "/join")
    @Operation(summary = "set role for a permission")
    public DefaultResponse join(@Valid @RequestBody GenericUpdateRequest<String, Set<String>> request){
        return permissionService.join(request);
    }
}
