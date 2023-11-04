package com.dh.bmn.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public EmailService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendWelcomeEmail(String to, String username) throws MessagingException {
        String htmlContent = processWelcomeEmail(username);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("¡Bienvenido a Bike me now!");
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

    private String processWelcomeEmail(String username) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("loginUrl", "http://localhost:5173/auth/login");
        return templateEngine.process("welcome-email", context);
    }
}
