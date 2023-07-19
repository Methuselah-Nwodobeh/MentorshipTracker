package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.services.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGeneratorImpl implements PasswordGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$&*~";
    @Override
    public String generatePassword(int length) {
        String regex = "^(?=[A-Z])(?=[a-z])(?=\\d)(?=[!@#$&*~]).{8,}$";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        while (!password.toString().matches(regex)) {
            password.setLength(0);

            for (int i = 0; i < length; i++) {
                int category = random.nextInt(4);
                switch (category) {
                    case 0 -> password.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
                    case 1 -> password.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
                    case 2 -> password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
                    case 3 -> password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
                    default -> password.append(UPPERCASE_LETTERS.charAt(random.nextInt(4)));
                }
            }
        }

        return password.toString();
    }
}
