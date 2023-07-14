package com.guidetrack.mentorship_tracker.repositories;

import com.guidetrack.mentorship_tracker.models.Permission;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByNameIgnoreCase(String name);

    Boolean existsByNameIgnoreCase(String name);

    @NotNull List<Permission> findAll();
}
