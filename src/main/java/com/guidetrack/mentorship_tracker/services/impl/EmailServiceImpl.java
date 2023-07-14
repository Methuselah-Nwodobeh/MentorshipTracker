package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Async
    public void sendEmail(String subject, String message, String senderAddress, String recipientAddress) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipientAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(senderAddress);
        log.info("this is email message {}", simpleMailMessage);

        try {
            emailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("Email failed to be sent", e);
        }
    }
}
