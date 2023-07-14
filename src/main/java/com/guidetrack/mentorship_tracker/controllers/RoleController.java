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
@RequestMapping("/api/v1/role/")
@Slf4j
public class RoleController {
    @Qualifier("roleServiceImpl")
    private final RoleAndPermissionService roleService;

    @PostMapping(path = "/create")
    @Operation(summary = "create a role")
    public DefaultResponse create(@Valid @RequestBody RoleAndPermissionRequest request){
        return roleService.create(request);
    }

    @PostMapping(path = "/update_name")
    @Operation(summary = "update the name of a role")
    public DefaultResponse updateName(@Valid @RequestBody GenericUpdateRequest<String, String> request){
        return roleService.updateName(request);
    }

    @PostMapping(path = "/update_description")
    @Operation(summary = "update the description of a role")
    public DefaultResponse updateDescription(@Valid @RequestBody GenericUpdateRequest<String, String> request){
        return roleService.updateDescription(request);
    }

    @PostMapping(path = "/delete")
    @Operation(summary = "delete a role")
    public DefaultResponse delete(@NotBlank @RequestBody String request){
        return roleService.delete(request);
    }

    @GetMapping(path = "/read")
    @Operation(summary = "read a role")
    public DefaultResponse read(@NotBlank @RequestBody String request){
        return roleService.read(request);
    }

    @PostMapping(path = "/read_all")
    @Operation(summary = "read all roles in the database")
    public DefaultResponse readAll(){
        return roleService.readAll();
    }

    @PostMapping(path = "/join")
    @Operation(summary = "set permission for a role")
    public DefaultResponse join(@Valid @RequestBody GenericUpdateRequest<String, Set<String>> request){
        log.info("this is request {}", request.identifier());
        return roleService.join(request);
    }
}
