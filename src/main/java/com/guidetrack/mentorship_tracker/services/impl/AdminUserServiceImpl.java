package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.user.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.requests.user.SignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.models.Admin;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.AdminRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.BasicUserService;
import com.guidetrack.mentorship_tracker.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.ERROR;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.SUCCESS;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements BasicUserService {
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    @Override
    public ResponseEntity<DefaultResponse> register(AdminSignupRequest request) {
        log.info("this is request {}", request);
        Admin admin = new Admin();
        Optional<Role> administrator = roleRepository.findByNameIgnoreCase("Administrator");
        log.info("this is admin role {}", administrator.orElse(null));

        boolean isAdminExists = adminRepository.existsByUsernameIgnoreCaseOrEmail(request.username(), request.email());
        if (isAdminExists) {
             return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, "Admin already exists"));
        }
        String password = request.password();

        String encodedPassword = passwordEncoder.encode(password);
        admin.setPassword(encodedPassword);

        admin.setEmail(request.email());
        if (request.username().isEmpty()) {
            admin.setUsername(request.email());
        }

        admin.setUsername(request.username());

        admin.setFirstname(request.firstname());

        if (administrator.isPresent()) {
            admin.setRole(administrator.get());
            admin.setVerified(false);
            adminRepository.save(admin);
        }
        emailService.sendEmail(
                    "Registration Confirmation",
                    "To confirm your email address, click this",
                    "methuselahnwodobeh@gmail.com",
                    request.email()
            );
        return ResponseEntity.status(201).body(new DefaultResponse(SUCCESS, admin.toString()));
    }

    @Override
    public ResponseEntity<DefaultResponse> register(SignupRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> login(LoginRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultResponse> update() {
        return null;
    }

    @Override
    public ResponseEntity<DefaultResponse> delete() {
        return null;
    }

    @Override
    public ResponseEntity<DefaultResponse> read() {
        return null;
    }

    @Override
    public ResponseEntity<DefaultResponse> readAll() {
        return null;
    }
}
