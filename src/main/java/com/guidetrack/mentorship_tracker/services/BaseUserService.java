package com.guidetrack.mentorship_tracker.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface BaseUserService {
    UserDetailsService userDetailsService();
}
