package com.lucas.clave.auth.email;

import com.lucas.clave.auth.email.model.EmailSend;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public void send(EmailSend email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setFrom(emailProperties.getFrom());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        mailSender.send(message);
    }
}
