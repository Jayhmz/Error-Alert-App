package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.enums.AlertStatus;
import com.plantaccion.alartapp.common.model.app.Alert;
import com.plantaccion.alartapp.common.model.app.Branch;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.model.app.Script;
import com.plantaccion.alartapp.common.repository.app.AlertRepository;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import com.plantaccion.alartapp.common.repository.app.ScriptRepository;
import com.plantaccion.alartapp.email.service.WriteEmailService;
import com.plantaccion.alartapp.exception.MailNotSentException;
import com.plantaccion.alartapp.exception.ScriptNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExecuteScriptServiceImpl implements ExecuteScriptService {
    private final JdbcTemplate template;
    private final AlertRepository alertRepository;
    private final ClusterRepository clusterRepository;
    private final ScriptRepository scriptRepository;
    private final WriteEmailService emailService;

    public ExecuteScriptServiceImpl(JdbcTemplate template, AlertRepository alertRepository,
                                    ClusterRepository clusterRepository, ScriptRepository scriptRepository,
                                    WriteEmailService emailService) {
        this.template = template;
        this.alertRepository = alertRepository;
        this.clusterRepository = clusterRepository;
        this.scriptRepository = scriptRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void executeQuery(Script script) {
        Script queriedScript = scriptRepository.findById(script.getId())
                .orElseThrow(() -> new ScriptNotFoundException("Script does not exist in our record"));
        Alert lastRecordedAlert = getLastRecordedAlert(queriedScript);

        for (Cluster cluster : getClusters()) {
            List<Alert> alertsForCluster = new ArrayList<>();
            for (Branch branch : cluster.getBranches()) {
                String newScript = queriedScript.getBody().replace("&branch", "'" + branch.getSolId() + "'");
                if (lastRecordedAlert != null) {
                    String completeQuery = newScript + " AND a.entry_date > '" + lastRecordedAlert.getEntryDate() + "'";
                    var result = template.queryForList(completeQuery);
                    createAlerts(result, queriedScript, cluster);
//                    alertsForCluster.addAll(createAlerts(result, queriedScript, cluster));
                } else {
                    List<Map<String, Object>> result = template.queryForList(newScript);
                    createAlerts(result, queriedScript, cluster);
//                    alertsForCluster.addAll(createAlerts(result, queriedScript, cluster));
                }
            }
            try {
                log.info("email sendig will be performed here...");
                //emailService.sendMail(cluster, queriedScript);
            } catch (MailNotSentException  e) {
                throw new MailNotSentException("Mail sending failed." + e.getCause());
            }
        }
    }

    private List<Alert> createAlerts(List<Map<String, Object>> result, Script queriedScript, Cluster cluster) {
        List<Alert> alerts = new ArrayList<>();
        for (Map<String, Object> record : result) {
            Alert alert = new Alert(queriedScript, AlertStatus.UNASSIGNED);
            alert.setEntryDate(LocalDateTime.parse(record.get("entry_date").toString()));
            alert.setTransactionDate(LocalDate.parse(record.get("tran_date").toString()));
            alert.setTranId(record.get("tran_id").toString());
            alert.setTranAmount((Double) record.get("tran_amt"));
            alert.setSolId((Integer) record.get("sol_id"));
            alert.setCluster(cluster);
            alerts.add(alert);
            alertRepository.save(alert);
            log.info("================= created Alert for " + alert.getAlertId()+" " +alert.getScript() +" "+ alert.getCluster()+" ===================");
        }
        return alerts;
    }


    private Alert getLastRecordedAlert(Script queriedScript) {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Alert> resultPage = alertRepository.findLastRecordByScriptTitle(queriedScript.getTitle(), pageable);
        if (resultPage != null && !resultPage.isEmpty()) {
            return resultPage.getContent().get(0);
        } else {
            return null;
        }
    }

    private List<Cluster> getClusters() {
        return clusterRepository.findAll();
    }

}
