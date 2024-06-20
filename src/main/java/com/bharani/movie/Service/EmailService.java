package com.bharani.movie.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMessage(MailBody body){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(body.to());

        message.setFrom("bharanitharan170802@gmail.com");

        message.setText(body.text());

        message.setSubject(body.subject());

        javaMailSender.send(message);

    }
}
