package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.models.Admin;
import com.guidetrack.mentorship_tracker.models.model_to_details.AdminDetails;
import com.guidetrack.mentorship_tracker.repositories.AdminRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.BaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements BaseUserService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<Admin> user = adminRepository.findByEmail(username);
            return new AdminDetails(user.orElseThrow(() -> new UsernameNotFoundException("User not found")), roleRepository);
        };
    }
}
