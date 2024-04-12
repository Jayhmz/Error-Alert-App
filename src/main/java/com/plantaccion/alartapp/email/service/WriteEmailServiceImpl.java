package com.plantaccion.alartapp.email.service;

import com.plantaccion.alartapp.common.model.app.Alert;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.model.app.Script;
import com.plantaccion.alartapp.common.repository.app.AlertRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
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
import java.util.List;

@Service
@Slf4j
public class WriteEmailServiceImpl implements WriteEmailService {
    private final JavaMailSender mailSender;
    private final ISpringTemplateEngine thymeleafTemplateEngine;
    private final AlertRepository alertRepository;
    private final ZCHProfileRepository ZCHProfileRepository;
    private final ICOProfileRepository icoProfileRepository;

    public WriteEmailServiceImpl(JavaMailSender mailSender, ISpringTemplateEngine thymeleafTemplateEngine, AlertRepository alertRepository, ZCHProfileRepository ZCHProfileRepository, ICOProfileRepository icoProfileRepository) {
        this.mailSender = mailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.alertRepository = alertRepository;
        this.ZCHProfileRepository = ZCHProfileRepository;
        this.icoProfileRepository = icoProfileRepository;
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendMail(Cluster cluster, List<Alert> alerts) throws MessagingException {
        if (alerts != null && !alerts.isEmpty()) {
            var zch = ZCHProfileRepository.findByCluster(cluster.getName());
            var controlOfficers = icoProfileRepository.findICOsBySupervisor(zch);
            String[] icoEmails = controlOfficers.stream()
                    .map(co -> co.getIcoStaff().getEmail())
                    .toArray(String[]::new);
            var zchEmail = zch.getStaff().getEmail();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(icoEmails);
            helper.setCc(zchEmail);

            Alert firstAlert = alerts.get(0);
//            helper.setSubject("Error alert for "+firstAlert.getScript().getTitle());
            helper.setSubject("Error alert for "+firstAlert.getScript());

            Context context = new Context();
            context.setVariable("alerts", alerts);
            context.setVariable("alertCount", alerts.size());
            context.setVariable("alert_group", firstAlert.getScript());
//            context.setVariable("alert_group", firstAlert.getScript().getTitle());

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
