package com.guidetrack.mentorship_tracker.services;

import org.springframework.stereotype.Service;

@Service
public interface PasswordGenerator {
    String generatePassword(int length);
}
