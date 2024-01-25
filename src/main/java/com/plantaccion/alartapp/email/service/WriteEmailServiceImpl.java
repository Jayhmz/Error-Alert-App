package com.plantaccion.alartapp.email.service;

import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.common.model.Cluster;
import com.plantaccion.alartapp.common.model.Script;
import com.plantaccion.alartapp.common.repository.AlertRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class WriteEmailServiceImpl implements WriteEmailService {
    private final JavaMailSender mailSender;
    private final ISpringTemplateEngine thymeleafTemplateEngine;
    private final AlertRepository alertRepository;

    public WriteEmailServiceImpl(JavaMailSender mailSender, ISpringTemplateEngine thymeleafTemplateEngine, AlertRepository alertRepository) {
        this.mailSender = mailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.alertRepository = alertRepository;
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendMail(List<Alert> alerts) throws MessagingException {
        log.info(">>>>>>>>>>>>>>> Inside the Send mail method <<<<<<<<<<<<<<<<");
        String[] emails = {"jayhmz10@gmail.com", "james.ogunrinola@fcmb.com", "jamesdamilare12@outlook.com"};

        for (String email : emails) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            if (alerts != null) {
                Alert firstAlert = alerts.get(0);
                helper.setSubject("Testing mail service for " + firstAlert.getScript().getTitle());
            }

            Context context = new Context();
            context.setVariable("alerts", alerts);
            if (alerts != null) {
                Alert firstAlert = alerts.get(0);
                context.setVariable("alert_group", firstAlert.getScript().getTitle());
            }

            // Process the Thymeleaf template
            String htmlContent = thymeleafTemplateEngine.process("mailTemplate.html", context);
            helper.setText(htmlContent, true);
            log.info("----------------------------->   Message has been sent successfully!");
            mailSender.send(message);
            for (Alert alert : alerts) {
                alert.setMailSent(true);
                alert.setMailSentOn(LocalDateTime.now());
            }
        }
    }

    @Override
    public void sendMail(Cluster cluster, Script queriedScript) throws MessagingException {
        var alerts = alertRepository.findAlertsByCluster(cluster, queriedScript);

        if (alerts != null && !alerts.isEmpty()) {
            String[] emails = {"jayhmz10@gmail.com", "james.ogunrinola@fcmb.com", "jamesdamilare12@outlook.com"};

            for (String email : emails) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(email);
                Alert firstAlert = alerts.get(0);
                helper.setSubject("Testing mail service for " + firstAlert.getScript().getTitle());

                Context context = new Context();
                context.setVariable("alerts", alerts);
                context.setVariable("alert_group", firstAlert.getScript().getTitle());

                // Process the Thymeleaf template
                String htmlContent = thymeleafTemplateEngine.process("mailTemplate.html", context);
                helper.setText(htmlContent, true);
                mailSender.send(message);
                log.info("----------------------------->   Message has been sent successfully!");
            }
            for (Alert alert : alerts) {
                alert.setMailSent(true);
                alert.setMailSentOn(LocalDateTime.now());
            }
        }
    }

}
