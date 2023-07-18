package com.guidetrack.mentorship_tracker.repositories;

import com.guidetrack.mentorship_tracker.models.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository  extends JpaRepository<Role, UUID> {

    Optional<Role> findByNameIgnoreCase(String role);
    @NotNull Optional<Role> findById(@NotNull UUID uuid);
    boolean existsByNameIgnoreCase(String role);
}
