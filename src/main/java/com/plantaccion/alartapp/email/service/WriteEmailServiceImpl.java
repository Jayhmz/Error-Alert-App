package com.plantaccion.alartapp.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WriteEmailServiceImpl implements WriteEmailService {
    private final JavaMailSender mailSender;

    public WriteEmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendMail() throws MessagingException {
        System.out.println(">>>>>>>>>>>>>>> Inside the Send mail method <<<<<<<<<<<<<<<<");
        String[] emails = {"jayhmz10@gmail.com", "jamesdamilare1996@gmail.com", "jamesdamilare12@outlook.com"};

        for (String email : emails) {
            MimeMessage message = mailSender.createMimeMessage();
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("Testing alert app email service");

            String content = "<html>" +
                    "<body>" +
                    "<table>" +
                    "Hello world, this is a test for the mail sending feature..."+
                    "</body>" +
                    " </html>";

            message.setContent(content, "text/html; charset=utf-8");
            System.out.println("----------------------------->   Message has been sent successfully!   ");
            mailSender.send(message);
        }
    }
}
