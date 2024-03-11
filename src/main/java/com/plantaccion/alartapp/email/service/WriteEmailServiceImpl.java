package com.plantaccion.alartapp.email.service;

import com.plantaccion.alartapp.common.model.app.Alert;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.model.app.Script;
import com.plantaccion.alartapp.common.repository.app.AlertRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.RCHProfileRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import java.time.LocalDateTime;

@Service
@Slf4j
public class WriteEmailServiceImpl implements WriteEmailService {
    private final JavaMailSender mailSender;
    private final ISpringTemplateEngine thymeleafTemplateEngine;
    private final AlertRepository alertRepository;
    private final RCHProfileRepository rchProfileRepository;
    private final ICOProfileRepository icoProfileRepository;

    public WriteEmailServiceImpl(JavaMailSender mailSender, ISpringTemplateEngine thymeleafTemplateEngine, AlertRepository alertRepository, RCHProfileRepository rchProfileRepository, ICOProfileRepository icoProfileRepository) {
        this.mailSender = mailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.alertRepository = alertRepository;
        this.rchProfileRepository = rchProfileRepository;
        this.icoProfileRepository = icoProfileRepository;
    }

    @Value("${spring.mail.username}")
    private String sender;


    @Override
    public void sendMail(Cluster cluster, Script queriedScript) throws MessagingException {
        var alerts = alertRepository.findAlertsByClusterAndScript(cluster, queriedScript);
        if (alerts != null && !alerts.isEmpty()) {
            var rch = rchProfileRepository.findByCluster(cluster);
            var controlOfficers = icoProfileRepository.findICOsByRCH(rch);
            String[] ccEmails = controlOfficers.stream()
                    .map(co -> co.getIcoStaff().getEmail())
                    .toArray(String[]::new);
            var rchEmail = rch.getStaff().getEmail();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(rchEmail);
            if(ccEmails.length > 0){
                helper.setCc(ccEmails);
            }
            Alert firstAlert = alerts.get(0);
            helper.setSubject("Testing mail service for " + firstAlert.getScript().getTitle() );

            Context context = new Context();
            context.setVariable("alerts", alerts);
            context.setVariable("alert_group", firstAlert.getScript().getTitle());

            // Process the Thymeleaf template
            String htmlContent = thymeleafTemplateEngine.process("mailTemplate.html", context);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("----------------------------->   Message has been sent successfully!");

            for (Alert alert : alerts) {
                alert.setMailSent(true);
                alert.setMailSentOn(LocalDateTime.now());
            }
        }
    }

}
