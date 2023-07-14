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
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.guidetrack.mentorship_tracker.utils.constants.ErrorConstants.ROLEDOESNOTEXIST;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.ERROR;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleAndPermissionService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
     @Override
    public DefaultResponse create(RoleAndPermissionRequest request) {
         Role role = new Role();
        boolean isRoleExists = roleRepository.existsByNameIgnoreCase(request.name());
        if (isRoleExists) {
            return new DefaultResponse(ERROR, "Role already exists");
        }
        role.setName(request.name());
        role.setDescription(request.description());
        roleRepository.save(role);
        return new DefaultResponse(SUCCESS, role.toString());
    }

    @Override
    public DefaultResponse updateName(GenericUpdateRequest<String, String> request) {
        Optional<Role> roleFromDB = getRoleFromDB(request.identifier());
        if (roleFromDB.isEmpty()) {
            return new DefaultResponse(ERROR, ROLEDOESNOTEXIST);
        }
        Role role = roleFromDB.get();
        role.setName(request.replacement());
        return new DefaultResponse(SUCCESS, role.toString());
    }

    @Override
    public DefaultResponse updateDescription(GenericUpdateRequest<String, String> request) {
        Optional<Role> roleFromDB = getRoleFromDB(request.identifier());
        if (roleFromDB.isEmpty()) {
            return new DefaultResponse(ERROR, ROLEDOESNOTEXIST);
        }
        Role role = roleFromDB.get();
        role.setDescription(request.replacement());
        return new DefaultResponse(SUCCESS, role.toString());
    }

    private Optional<Role> getRoleFromDB(String name) {
        return roleRepository.findByNameIgnoreCase(name);
    }

    @Override
    public DefaultResponse delete(String name) {
        Optional<Role> roleFromDB = getRoleFromDB(name);
        if (roleFromDB.isEmpty()) {
            return new DefaultResponse(ERROR, ROLEDOESNOTEXIST);
        }
        roleRepository.delete(roleFromDB.get());
        return new DefaultResponse(SUCCESS, "Role deleted");
    }

    @Override
    public DefaultResponse read(String name) {
        Optional<Role> roleFromDB = getRoleFromDB(name);
        return roleFromDB.map(role -> new DefaultResponse(SUCCESS,
                convertPermissionToHashMap(role).toString())).orElseGet(() -> new DefaultResponse(ERROR,
                "Permission does not exist"));
    }

    @Override
    public DefaultResponse readAll() {
        List<Map<String, Object>> allRoles = getAllRolesAsHashMap();
        return new DefaultResponse(SUCCESS, allRoles.toString());
    }

    @Override
    public DefaultResponse join(@NotNull GenericUpdateRequest<String, Set<String>> request) {
        Optional<Role> optionalRole = getRoleFromDB(request.identifier());
        Role role = optionalRole.orElseThrow(() -> new BadRequestException(ROLEDOESNOTEXIST));
        Set<Permission> permissions = new HashSet<>();
        for (String permissionName:request.replacement()) {
            Optional<Permission> permissionOptional = permissionRepository.findByNameIgnoreCase(permissionName);
            Permission permissionFromOptional = permissionOptional.orElseThrow(() -> new BadRequestException("Permission Does not exist"));
            permissions.add(permissionFromOptional);
        }
        role.setPermissions(permissions);
        roleRepository.save(role);

        return new DefaultResponse(SUCCESS, "Role set successfully");
    }

    public List<Map<String, Object>> getAllRolesAsHashMap() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(this::convertPermissionToHashMap)
                .toList();
    }

    private Map<String, Object> convertPermissionToHashMap(Role role) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", role.getId());
        map.put("name", role.getName());
        map.put("description", role.getDescription());
        map.put("permissions", role.getPermissions());
        map.put("date_created", role.getDateCreated());
        map.put("date_modified", role.getDateModified());


        return map;
    }
}
