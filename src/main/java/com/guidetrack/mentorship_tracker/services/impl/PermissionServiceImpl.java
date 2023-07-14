package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.dto.requests.GenericUpdateRequest;
import com.guidetrack.mentorship_tracker.dto.requests.RoleAndPermissionRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.exceptions.BadRequestException;
import com.guidetrack.mentorship_tracker.models.Permission;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.PermissionRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.RoleAndPermissionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import static com.guidetrack.mentorship_tracker.utils.constants.ErrorConstants.PERMISSIONDOESNOTEXIST;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.ERROR;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements RoleAndPermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public DefaultResponse create(@Valid RoleAndPermissionRequest request) {
        Permission permission = new Permission();
        boolean isPermissionExists = permissionRepository.existsByNameIgnoreCase(request.name());
        if (isPermissionExists) {
            return new DefaultResponse(ERROR, "Permission already exists");
        }
        permission.setName(request.name());
        permission.setDescription(request.description());
        permissionRepository.save(permission);
        return new DefaultResponse(SUCCESS, permission.toString());
    }

    @Override
    public DefaultResponse updateName(@Valid GenericUpdateRequest<String, String> request) {
        Optional<Permission> permissionFromDB = getPermissionFromDB(request.identifier());
        if (permissionFromDB.isEmpty()) {
            return new DefaultResponse(ERROR, PERMISSIONDOESNOTEXIST);
        }
        Permission permission = permissionFromDB.get();
        permission.setName(request.replacement());
        return new DefaultResponse(SUCCESS, permission.toString());
    }

    @Override
    public DefaultResponse updateDescription(@Valid GenericUpdateRequest<String, String> request) {
        Optional<Permission> permissionFromDB = getPermissionFromDB(request.identifier());
        if (permissionFromDB.isEmpty()) {
            return new DefaultResponse(ERROR, PERMISSIONDOESNOTEXIST);
        }
        Permission permission = permissionFromDB.get();
        permission.setDescription(request.replacement());
        return new DefaultResponse(SUCCESS, permission.toString());
    }

    private Optional<Permission> getPermissionFromDB(@Valid String name) {
        return permissionRepository.findByNameIgnoreCase(name);
    }

    @Override
    public DefaultResponse delete(@NotBlank @RequestBody String name) {
        Optional<Permission> permissionFromDB = getPermissionFromDB(name);
        if (permissionFromDB.isEmpty()) {
            return new DefaultResponse(ERROR, PERMISSIONDOESNOTEXIST);
        }
        permissionRepository.delete(permissionFromDB.get());
        return new DefaultResponse(SUCCESS, "Permission deleted");
    }

    @Override
    public DefaultResponse read(@NotBlank @RequestBody String name) {
        Optional<Permission> permissionFromDB = getPermissionFromDB(name);
        return permissionFromDB.map(permission -> new DefaultResponse(SUCCESS,
                convertPermissionToHashMap(permission).toString())).orElseGet(() -> new DefaultResponse(ERROR,
                PERMISSIONDOESNOTEXIST));
    }

    @Override
    public DefaultResponse readAll() {
        List<Map<String, Object>> allPermissions = getAllPermissionsAsHashMap();
        return new DefaultResponse(SUCCESS, allPermissions.toString());
    }

    @Override
    public DefaultResponse join(@NotNull GenericUpdateRequest<String, Set<String>> request) {
        Optional<Permission> optionalPermission = getPermissionFromDB(request.identifier());
        Permission permission = optionalPermission.orElseThrow(() -> new BadRequestException(PERMISSIONDOESNOTEXIST));
        Set<Role> roles = new HashSet<>();
        for (String roleName:request.replacement()) {
            Optional<Role> roleOptional = roleRepository.findByNameIgnoreCase(roleName);
            Role role = roleOptional.orElseThrow(() -> new BadRequestException("Role Does not exist"));
            roles.add(role);
        }
        permission.setRoles(roles);
        permissionRepository.save(permission);

        return new DefaultResponse(SUCCESS, "Role set successfully");
    }

    public List<Map<String, Object>> getAllPermissionsAsHashMap() {
        List<Permission> entities = permissionRepository.findAll();

        return entities.stream()
                .map(this::convertPermissionToHashMap)
                .toList();
    }

    private Map<String, Object> convertPermissionToHashMap(Permission permission) {
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
