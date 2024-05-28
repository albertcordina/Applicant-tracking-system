package com.example.bcrypt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /*
         JavaMailSender provides a robust and flexible way to integrate email functionality into Spring applications,
          leveraging the power of the JavaMail API with the simplicity and configurability of the Spring framework.
     */
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        /*
            SimpleMailMessage is a convenient utility for handling basic email requirements within a Spring application,
             making it easy to send plain text emails with minimal setup. For more complex email needs
                     (e.g., HTML content, attachments), MimeMessage is typically used instead.
         */
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
