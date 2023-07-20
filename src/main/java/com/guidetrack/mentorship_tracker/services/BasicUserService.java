package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.user.LoginRequest;
import com.guidetrack.mentorship_tracker.dto.requests.user.SignupRequest;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import com.guidetrack.mentorship_tracker.dto.responses.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BasicUserService {
    ResponseEntity<DefaultResponse> register(AdminSignupRequest request);
    ResponseEntity<DefaultResponse> register(SignupRequest request);

    ResponseEntity<JwtAuthenticationResponse> login(LoginRequest request);

    ResponseEntity<DefaultResponse> update();

    ResponseEntity<DefaultResponse> delete();

    ResponseEntity<DefaultResponse> read();

    ResponseEntity<DefaultResponse> readAll();
}
