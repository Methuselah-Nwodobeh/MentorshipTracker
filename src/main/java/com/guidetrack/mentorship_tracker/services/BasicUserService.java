package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.requests.SignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface BasicUserService {
    DefaultResponse register(AdminSignupRequest request);
    DefaultResponse register(SignupRequest request);

    JwtAuthenticationResponse login(LoginRequest request);

    DefaultResponse update();

    DefaultResponse delete();

    DefaultResponse read();

    DefaultResponse readAll();
}
