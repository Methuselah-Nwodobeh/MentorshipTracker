package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.dto.requests.role.RoleRequest;
import com.guidetrack.mentorship_tracker.dto.requests.role.UpdateRoleRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.models.Permission;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.PermissionRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.RoleService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.guidetrack.mentorship_tracker.utils.constants.ErrorConstants.PERMISSIONDOESNOTEXIST;
import static com.guidetrack.mentorship_tracker.utils.constants.ErrorConstants.ROLEDOESNOTEXIST;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.ERROR;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public ResponseEntity<DefaultResponse> create(RoleRequest request) {
        Role role = new Role();
        boolean isRoleExists = roleRepository.existsByNameIgnoreCase(request.name());
        if (isRoleExists) {
            return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, "Role already exists"));
        }
        role.setName(request.name());
        role.setDescription(request.description());
        roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DefaultResponse(SUCCESS, role.toString()));
    }

    @Override
    public ResponseEntity<DefaultResponse> update(@NotNull UpdateRoleRequest request) {
        Optional<Role> roleFromDB = getRoleFromDB(request.uuid());
        if (roleFromDB.isEmpty()) {
            return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, ROLEDOESNOTEXIST));
        }
        Role role = roleFromDB.get();
        if (request.name().isEmpty() && request.description().isEmpty() && request.permissions().isEmpty()) {
            return ResponseEntity.status(400).body(new DefaultResponse(ERROR, "At least one field must be set excluding UUID"));
        }
        if (!request.name().isEmpty()) {
            role.setName(request.name());
        }
        if (!request.description().isEmpty()) {
            role.setDescription(request.description());
        }
        Set<Permission> permissionSet = role.getPermissions();
        if (!request.permissions().isEmpty()) {
            for (String permission : request.permissions()) {
                Optional<Permission> optionalPermission = permissionRepository.findByNameIgnoreCase(permission);
                if (optionalPermission.isEmpty()) {
                    return ResponseEntity.status(404).body(new DefaultResponse(ERROR, PERMISSIONDOESNOTEXIST));
                }
                Permission permission1 = optionalPermission.get();
                permissionSet.add(permission1);
            }
        }
        roleRepository.save(role);
        return ResponseEntity.status(201).body(new DefaultResponse(SUCCESS, role.toString()));
    }

    @Override
    public ResponseEntity<DefaultResponse> delete(UUID uuid) {
        Optional<Role> optionalRole = getRoleFromDB(uuid);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, ROLEDOESNOTEXIST));
        }
        roleRepository.delete(optionalRole.get());
        return ResponseEntity.status(200).body(new DefaultResponse(SUCCESS, "Role deleted"));
    }

    @Override
    public ResponseEntity<DefaultResponse> read(UUID uuid) {
        Optional<Role> optionalRole = getRoleFromDB(uuid);
        return optionalRole.map(role -> ResponseEntity
                        .status(200)
                        .body(new DefaultResponse(ERROR, convertRoleToHashMap(role))))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .body(new DefaultResponse(ERROR, ROLEDOESNOTEXIST)));
    }

    @Override
    public ResponseEntity<DefaultResponse> readAll() {
        return ResponseEntity.status(200).body(new DefaultResponse(SUCCESS, getAllRolesAsHashMap()));
    }

    private Optional<Role> getRoleFromDB(UUID uuid) {
        return roleRepository.findById(uuid);
    }

    private Map<String, Object> convertRoleToHashMap(Role role) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", role.getId());
        map.put("name", role.getName());
        map.put("description", role.getDescription());
        map.put("permissions", role.getPermissions());
        map.put("date_created", role.getDateCreated());
        map.put("date_modified", role.getDateModified());

        return map;
    }

    public List<Map<String, Object>> getAllRolesAsHashMap() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(this::convertRoleToHashMap)
                .toList();
    }
}
