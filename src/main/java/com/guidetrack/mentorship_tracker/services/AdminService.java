package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.dto.responses.SignUpResponse;
import com.guidetrack.mentorship_tracker.models.Admin;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.models.model_to_details.AdminDetails;
import com.guidetrack.mentorship_tracker.repositories.AdminRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.impl.JwtServiceImpl;
import com.guidetrack.mentorship_tracker.utils.encryption.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.guidetrack.mentorship_tracker.utils.encryption.constants.SeederConstants.ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final JwtServiceImpl jwtService = new JwtServiceImpl();

    private static final String SUCCESS = "Success";
    private static final String ERROR = "Error";

    public SignUpResponse registerAdmin(AdminSignupRequest adminRequest) {
        log.info("this is adminRequest {}", adminRequest);
        Admin admin = new Admin();
        Optional<Role> administrator = roleRepository.findByNameIgnoreCase(ADMIN);
        log.info("this is admin role {}", administrator.orElse(null));

        boolean isAdminExists = adminRepository.existsByUsernameIgnoreCaseOrEmail(adminRequest.username(), adminRequest.email());
        if (isAdminExists) {
            return new SignUpResponse(ERROR, "Admin already exists");
        }
        String password = adminRequest.password();

        if (password == null || password.isEmpty()) {
            return new SignUpResponse(ERROR, "Password is empty");
        }
        String encodedPassword = passwordEncrypt.encodePassword(password);
        admin.setPassword(encodedPassword);
        if (adminRequest.email().isEmpty()){
            return new SignUpResponse(ERROR, "Email is empty");
        }
        admin.setEmail(adminRequest.email());
        if (adminRequest.username().isEmpty()) {
            admin.setUsername(adminRequest.email());
        }
        admin.setUsername(adminRequest.username());
        if (adminRequest.firstname().isEmpty()) {
            return new SignUpResponse(ERROR, "Firstname is empty");
        }
        admin.setFirstname(adminRequest.firstname());
        if (administrator.isPresent()) {
            admin.setRole(administrator.get());
            admin.setVerified(false);
            adminRepository.save(admin);
        }
        emailService.sendEmail(
                    "Registration Confirmation",
                    "To confirm your email address, click this",
                    "methuselahnwodobeh@gmail.com",
                    adminRequest.email()
            );
        return new SignUpResponse(SUCCESS, admin.toString());
    }

    public JwtAuthenticationResponse signInAdmin(LoginRequest request) {
        Optional<Admin> admin = adminRepository.findByEmail(request.email());
        if (admin.isEmpty()) {
            return new JwtAuthenticationResponse(ERROR, "Email is invalid", null);
        }
        String password = request.password();
        if (!passwordEncrypt.verifyPassword(password, admin.get().getPassword())) {
            return new JwtAuthenticationResponse(ERROR, "Password is invalid", null);
        }
        if (!admin.get().isVerified()) {
            return new JwtAuthenticationResponse(ERROR, "You are not verified", null);
        }
        AdminDetails adminDetails = new AdminDetails(admin.get(), roleRepository);
        String accessToken = jwtService.generateToken(adminDetails);
        String refreshToken = jwtService.generateRefreshToken(adminDetails);
        return JwtAuthenticationResponse
                .builder().
                refreshToken(refreshToken)
                .accessToken(accessToken)
                .status("success")
                .build();
    }
}
