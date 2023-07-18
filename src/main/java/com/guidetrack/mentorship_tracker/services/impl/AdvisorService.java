package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.user.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.requests.user.SignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.models.Advisor;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.BaseUserRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.BasicUserService;
import com.guidetrack.mentorship_tracker.services.EmailService;
import com.guidetrack.mentorship_tracker.services.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.ERROR;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvisorService implements BasicUserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final BaseUserRepository baseUserRepository;
    private final PasswordGenerator passwordGenerator;
    @Override
    public ResponseEntity<DefaultResponse> register(AdminSignupRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultResponse> register(SignupRequest request) {
        boolean isAdvisorExists = baseUserRepository.existsByUsernameIgnoreCaseOrEmail(request.username(), request.email());
        if (isAdvisorExists){
            return ResponseEntity.badRequest().body(new DefaultResponse(ERROR, "Advisor already exists"));
        }
        Advisor advisor = new Advisor();
        Optional<Role> optionalRole = roleRepository.findByNameIgnoreCase(request.role());
        String newPassword = passwordGenerator.generatePassword(8);
        advisor.setPassword(passwordEncoder.encode(newPassword));
        advisor.setUsername(request.username());
        advisor.setRole(optionalRole.orElse(null));

        advisor.setVerified(false);
        baseUserRepository.save(advisor);
        emailService.sendEmail(
                "Welcome To GuideTrack",
                String.format("""
                        Welcome to GuideTrack.
                        This is your new password %s.
                        Login to have your account verified.
                        """, newPassword),
                "methuselahnwodobeh@gmail.com",
                request.email()
        );
        return ResponseEntity.status(201).body(new DefaultResponse(SUCCESS, advisor.toString()));
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
