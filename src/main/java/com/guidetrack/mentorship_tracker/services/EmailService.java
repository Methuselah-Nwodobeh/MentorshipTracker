package com.guidetrack.mentorship_tracker.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    @Async
    void sendEmail(String subject, String message, String senderAddress, String recipientAddress);
}
