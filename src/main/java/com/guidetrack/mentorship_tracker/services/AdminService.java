package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dao.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dao.requests.LoginRequest;
import com.guidetrack.mentorship_tracker.dao.responses.JwtAuthenticationResponse;
import com.guidetrack.mentorship_tracker.dao.responses.SignUpResponse;
import com.guidetrack.mentorship_tracker.exceptions.BadRequestException;
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

    public SignUpResponse registerAdmin(AdminSignupRequest adminRequest) {
        log.info("this is adminRequest {}", adminRequest);
        Admin admin = new Admin();
        Optional<Role> administrator = roleRepository.findByNameIgnoreCase(ADMIN);
        log.info("this is admin role {}", administrator.orElse(null));

        boolean isAdminExists = adminRepository.existsByUsernameIgnoreCaseOrEmail(adminRequest.username(), adminRequest.email());
        if (isAdminExists) {
            throw new BadRequestException("Admin already exists");
        }
        String password = adminRequest.password();

        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Invalid Password");
        }
        String encodedPassword = passwordEncrypt.encodePassword(password);
        admin.setPassword(encodedPassword);
        if (adminRequest.email().isEmpty()){
            throw new BadRequestException("Email is null");
        }
        admin.setEmail(adminRequest.email());
        if (adminRequest.username().isEmpty()) {
            admin.setUsername(adminRequest.email());
        }
        admin.setUsername(adminRequest.username());
        if (adminRequest.firstname().isEmpty()) {
            throw new BadRequestException("Invalid firstname");
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
        return new SignUpResponse("success", admin.toString());
    }

    public JwtAuthenticationResponse signInAdmin(LoginRequest request) {
        Optional<Admin> admin = adminRepository.findByEmail(request.email());
        if (admin.isEmpty()) {
            throw new BadRequestException("Invalid email");
        }
        String password = request.password();
        if (!passwordEncrypt.verifyPassword(password, admin.get().getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        if (!admin.get().isVerified()) {
            throw new BadRequestException("You are not verified");
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
