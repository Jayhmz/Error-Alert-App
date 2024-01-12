package com.plantaccion.alartapp.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class WriteEmailServiceImpl implements WriteEmailService {
    private final JavaMailSender mailSender;

    public WriteEmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("jayhmz10@gmail.com");
        message.setSubject("Testing alert app email service");
        message.setText("" +
                "<html>" +
                "<body>" +
                "<table>" +
                "<tr style='font-style=bold; font-size=20px; color=blue;'>" +
                "<th>Header 1</th>" + "<th>Header 2</th>" + "<th>Header 3</th>" +
                "</tr>" +
                "<tr style='font-style=bold; font-size=20px; color=blue;'>" +
                "<td>Body 1</td>" + "<td>Body 2</td>" + "<td>Body 3</td>" +
                "</tr>" +
                " </table>" +
                "</body>" +
                " </html>");
        message.setFrom(sender);
            mailSender.send(message);
    }
}
