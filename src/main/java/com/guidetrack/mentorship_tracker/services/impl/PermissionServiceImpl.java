package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.dto.requests.permission.PermissionRequest;
import com.guidetrack.mentorship_tracker.dto.requests.permission.UpdatePermissionRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.models.Permission;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.PermissionRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.PermissionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import static com.guidetrack.mentorship_tracker.utils.constants.ErrorConstants.PERMISSIONDOESNOTEXIST;
import static com.guidetrack.mentorship_tracker.utils.constants.ErrorConstants.ROLEDOESNOTEXIST;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.ERROR;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<DefaultResponse> create(@Valid @NotNull PermissionRequest request) {
        Permission permission = new Permission();
        boolean isPermissionExists = permissionRepository.existsByNameIgnoreCase(request.name());
        if (isPermissionExists) {
            return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, "Permission already exists"));
        }
        permission.setName(request.name());
        permission.setDescription(request.description());
        permissionRepository.save(permission);
        return ResponseEntity.status(201).body(new DefaultResponse(SUCCESS, permission.toString()));

    }

    @Override
    public ResponseEntity<DefaultResponse> update(@Valid @NotNull UpdatePermissionRequest request) {
        Optional<Permission> optionalPermission = getPermissionFromDB(request.uuid());
        if (optionalPermission.isEmpty()) {
            return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, PERMISSIONDOESNOTEXIST));
        }
        Permission permission = optionalPermission.get();
        if (request.name().isEmpty() && request.description().isEmpty() && request.roles().isEmpty()) {
            return ResponseEntity.status(400).body(new DefaultResponse(ERROR, "At least one field must be set excluding UUID"));
        }
        if (!request.name().isEmpty()) {
            permission.setName(request.name());
        }
        if (!request.description().isEmpty()) {
            permission.setDescription(request.description());
        }
        Set<Role> roleSet = permission.getRoles();
        if (!request.roles().isEmpty()) {
            for (String role : request.roles()) {
                Optional<Role> optionalRole = roleRepository.findByNameIgnoreCase(role);
                if (optionalRole.isEmpty()) {
                    return ResponseEntity.status(404).body(new DefaultResponse(ERROR, ROLEDOESNOTEXIST));
                }
                Role role1 = optionalRole.get();
                roleSet.add(role1);
            }
        }
        permissionRepository.save(permission);
        return ResponseEntity.status(201).body(new DefaultResponse(SUCCESS, permission.toString()));
    }

    @Override
    public ResponseEntity<DefaultResponse> delete(@NotBlank @RequestBody UUID uuid) {
        Optional<Permission> optionalPermission = getPermissionFromDB(uuid);
        if (optionalPermission.isEmpty()) {
            return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, PERMISSIONDOESNOTEXIST));
        }
        permissionRepository.delete(optionalPermission.get());
        return ResponseEntity.status(200).body(new DefaultResponse(SUCCESS, "Permission deleted"));
    }

    @Override
    public ResponseEntity<DefaultResponse> read(@NotBlank @RequestBody UUID uuid) {
        Optional<Permission> optionalPermission = getPermissionFromDB(uuid);
        return optionalPermission.map(permission -> ResponseEntity
                .status(200)
                .body(new DefaultResponse(SUCCESS, convertPermissionToHashMap(permission))))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .body(new DefaultResponse(ERROR, PERMISSIONDOESNOTEXIST)));
    }

    @Override
    public ResponseEntity<DefaultResponse> readAll() {
        return ResponseEntity.status(200).body(new DefaultResponse(SUCCESS, getAllPermissionsAsHashMap()));
    }

    private @NotNull Optional<Permission> getPermissionFromDB(UUID uuid) {
        return permissionRepository.findById(uuid);
    }

    public List<Map<String, Object>> getAllPermissionsAsHashMap() {
        List<Permission> entities = permissionRepository.findAll();

        return entities.stream()
                .map(this::convertPermissionToHashMap)
                .toList();
    }

    private @NotNull Map<String, Object> convertPermissionToHashMap(@NotNull Permission permission) {
        Map<String, Object> map = new HashMap<>();

        // Add your desired fields to the HashMap
        map.put("id", permission.getId());
        map.put("name", permission.getName());
        map.put("description", permission.getDescription());
        map.put("roles", permission.getDescription());
        map.put("date_created", permission.getDateCreated());
        map.put("date_modified", permission.getDateModified());
        // ...

        return map;
    }
}
