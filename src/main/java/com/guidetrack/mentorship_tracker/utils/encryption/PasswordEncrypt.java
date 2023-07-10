package com.guidetrack.mentorship_tracker.utils.encryption;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Component
public class PasswordEncrypt {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncrypt() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Bean
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Bean
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
