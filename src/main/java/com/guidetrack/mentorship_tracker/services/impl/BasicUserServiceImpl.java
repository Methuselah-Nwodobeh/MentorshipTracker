package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.requests.SignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import com.guidetrack.mentorship_tracker.models.model_to_details.BaseUserDetails;
import com.guidetrack.mentorship_tracker.repositories.BaseUserRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.BasicUserService;
import com.guidetrack.mentorship_tracker.services.EmailService;
import com.guidetrack.mentorship_tracker.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.ERROR;
import static com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicUserServiceImpl implements BasicUserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final BaseUserRepository baseUserRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public DefaultResponse register(AdminSignupRequest request) {
        return null;
    }

    @Override
    public DefaultResponse register(SignupRequest request) {
        log.info("this is request {}", request);
        BaseUserModel user = new BaseUserModel();
        Optional<Role> roleFromDB = roleRepository.findByNameIgnoreCase(request.role());
        log.info("this is user role {}", roleFromDB.orElse(null));

        boolean isAdminExists = baseUserRepository.existsByUsernameIgnoreCaseOrEmail(request.username(), request.email());
        if (isAdminExists) {
            return new DefaultResponse(ERROR, "Admin already exists");
        }
        String password = request.password();

        user.setPassword(passwordEncoder.encode(password));

        user.setUsername(request.username().isEmpty() ? request.email() : request.username());

        user.setRole(roleFromDB.orElse(null));

        user.setVerified(false);

        baseUserRepository.save(user);
            
        emailService.sendEmail(
                "Registration Confirmation",
                "To confirm your email address, click this",
                "methuselahnwodobeh@gmail.com",
                request.email()
        );
        return new DefaultResponse(SUCCESS, user.toString());
    }

    @Override
    public JwtAuthenticationResponse login(@Valid LoginRequest request) {
        log.info("this is request {}", request.email());

        UsernamePasswordAuthenticationToken authreq = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        log.info("This is auth token class {}", authreq);
         Authentication authentication = authenticationManager.authenticate(authreq);
         log.info("User is authenticated {}", authentication);
         BaseUserModel userFromDB = baseUserRepository.findByEmail(request.email()).orElseThrow(
                 () -> new IllegalArgumentException("Invalid email or password"));
         log.info("this is user {}", userFromDB.getUsername());
        if (!userFromDB.isVerified()) {
            return new JwtAuthenticationResponse(ERROR, "You are not verified", null);
        }
        BaseUserDetails userDetails = new BaseUserDetails(userFromDB);
        log.info("this is userdetails {}", userDetails.getAuthorities());
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return JwtAuthenticationResponse
                .builder().
                refreshToken(refreshToken)
                .accessToken(accessToken)
                .status(SUCCESS)
                .build();
    }

    @Override
    public DefaultResponse update() {
        return null;
    }

    @Override
    public DefaultResponse delete() {
        return null;
    }

    @Override
    public DefaultResponse read() {
        return null;
    }

    @Override
    public DefaultResponse readAll() {
        return null;
    }
}
