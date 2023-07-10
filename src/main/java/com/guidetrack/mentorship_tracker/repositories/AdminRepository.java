package com.guidetrack.mentorship_tracker.repositories;

import com.guidetrack.mentorship_tracker.models.Admin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByUsernameIgnoreCaseOrEmail(@NotNull String username, @Email @NotNull String email);
}
