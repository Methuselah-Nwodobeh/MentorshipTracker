package com.guidetrack.mentorship_tracker.utils.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@RequiredArgsConstructor
public class PasswordEncrypt {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncrypt() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
