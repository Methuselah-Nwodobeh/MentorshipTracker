package com.guidetrack.mentorship_tracker.repositories;

import com.guidetrack.mentorship_tracker.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Permission findByName(String name);

    Boolean existsByName(String name);
}
