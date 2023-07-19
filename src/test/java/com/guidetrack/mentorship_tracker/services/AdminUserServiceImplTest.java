package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.models.Admin;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.AdminRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminUserServiceImplTest {
    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;
    private RoleRepository roleRepository;
    private EmailService emailService;
    private AdminUserServiceImpl adminUserService;

    @BeforeEach
    public void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        adminRepository = mock(AdminRepository.class);
        roleRepository = mock(RoleRepository.class);
        emailService = mock(EmailService.class);
        adminUserService = new AdminUserServiceImpl(passwordEncoder, adminRepository, roleRepository, emailService);
    }

    @Test
    void testRegister() {
        // Given
        AdminSignupRequest request = new AdminSignupRequest("jikky", "tikky", "haseve2520@muzitp.com", "passy@1");

        Role administratorRole = new Role();
        administratorRole.setId(UUID.randomUUID());
        administratorRole.setName("Administrator");

        when(roleRepository.findByNameIgnoreCase("Administrator")).thenReturn(Optional.of(administratorRole));
        when(adminRepository.existsByUsernameIgnoreCaseOrEmail(request.username(), request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("hashedpassword");

        // When
        ResponseEntity<DefaultResponse> response = adminUserService.register(request);

        // Then
        verify(adminRepository).save(any(Admin.class));
        verify(emailService).sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
        assert(response.getStatusCode().equals(HttpStatus.CREATED));
    }
}