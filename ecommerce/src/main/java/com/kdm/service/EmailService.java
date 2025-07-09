package com.kdm.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender; // Fixed camelCase issue

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) throws MessagingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true); // Enable HTML content
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom("kdurgamaheshm@gmail.com"); // Set sender email

            javaMailSender.send(mimeMessage); // Fixed incorrect variable usage

            logger.info("Email sent successfully to {}", userEmail);
        } catch (MailException e) {
            logger.error("Error while sending email to {}: {}", userEmail, e.getMessage(), e);
            throw new MailSendException("Failed to send email: " + e.getMessage());
        }
    }
}
