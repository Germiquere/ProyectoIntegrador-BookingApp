package com.dh.bmn.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendWelcomeEmail(String to, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("¡Bienvenido a Bike me now!");
        String url = "http://localhost:5173/auth/login";
        message.setText("¡Hola " + username + "! Gracias por registrarte en Bike Me Now. Haz clic en el siguiente enlace para iniciar sesión: " + url);
        emailSender.send(message);
    }
}
