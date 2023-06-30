package com.guidetrack.mentorship_tracker.repositories;

import com.guidetrack.mentorship_tracker.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByEmail(String email);

    Boolean existsByEmail(String email);
}
