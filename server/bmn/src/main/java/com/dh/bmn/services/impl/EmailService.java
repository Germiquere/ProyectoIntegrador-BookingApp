package com.dh.bmn.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;


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
        context.setVariable("loginUrl", "http://54.146.13.35/auth/login"); //TODO cambiar el localhost por el dominio o ip del server
        return templateEngine.process("welcome-email", context);
    }

    public void sendReservationEmail(String to, String username, String bicicleta, LocalDate fechaInicio, LocalDate fechaFin) throws MessagingException {
        String htmlContent = processReservationEmail(username, bicicleta, fechaInicio, fechaFin);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("¡Reserva confirmada!");
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

    private String processReservationEmail(String username, String bicicleta, LocalDate fechaInicio, LocalDate fechaFin) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("home", "http://54.146.13.35");
        context.setVariable("bicicleta", bicicleta);
        context.setVariable("fechaInicio", fechaInicio);
        context.setVariable("fechaFin", fechaFin);
        context.setVariable("actualizarReserva", "http://54.146.13.35/bike-me-now/api/reservas"); //TODO modificar a la url de la modificacion
        return templateEngine.process("reservation-email", context);
    }
}
