package com.guidetrack.mentorship_tracker.repositories;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BaseUserRepository extends JpaRepository<BaseUserModel, UUID> {
    Optional<BaseUserModel> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByUsernameIgnoreCaseOrEmail(@NotNull String username, @Email @NotNull String email);
}
