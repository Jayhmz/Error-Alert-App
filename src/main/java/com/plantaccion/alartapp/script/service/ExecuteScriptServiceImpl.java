package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.enums.AlertStatus;
import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.common.model.Branch;
import com.plantaccion.alartapp.common.model.Cluster;
import com.plantaccion.alartapp.common.model.Script;
import com.plantaccion.alartapp.common.repository.AlertRepository;
import com.plantaccion.alartapp.common.repository.ClusterRepository;
import com.plantaccion.alartapp.common.repository.ScriptRepository;
import com.plantaccion.alartapp.email.service.WriteEmailService;
import com.plantaccion.alartapp.exception.MailNotSentException;
import com.plantaccion.alartapp.exception.ScriptNotFoundException;
import jakarta.transaction.Transactional;
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
    public List<Map<String, Object>> executeQuery(Script script) {
        List<Map<String, Object>> aggregatedQueryResult = new ArrayList<>();
        Script queriedScript = scriptRepository.findById(script.getId())
                .orElseThrow(() -> new ScriptNotFoundException("Script does not exist in our record"));
        Alert lastRecordedAlert = getLastRecordedAlert(queriedScript);
        for (Cluster cluster : getClusters()) {
            System.out.println("------------ Inside the Cluster loop --------------");
            for (Branch branch : cluster.getBranches()) {
                System.out.println(">>>>>>>>>>>>>>> Inside the branch loop <<<<<<<<<<<<<<<<");
                String replacedQuery = queriedScript.getBody().replace("&branch", "'" + branch.getSolId() + "'");
                if (lastRecordedAlert != null) {
                    String completeQuery = replacedQuery + " AND a.entry_date > '" + lastRecordedAlert.getEntryDate() + "'";
                    List<Map<String, Object>> queryResult = template.queryForList(completeQuery);
                    aggregatedQueryResult.addAll(queryResult);
                } else {
                    List<Map<String, Object>> queryResult = template.queryForList(replacedQuery);
                    aggregatedQueryResult.addAll(queryResult);
                }
                System.out.println(">>>>>>>>>>>>>>> Completed the branch loop <<<<<<<<<<<<<<<<");
            }

            if (!aggregatedQueryResult.isEmpty()) {
                System.out.println("<<<<<<<<<<<< Inside the aggregated Result loop >>>>>>>>>>>>>>");
                Map<String, Object> firstRecord = aggregatedQueryResult.get(0);
                List<String> headers = new ArrayList<>(firstRecord.keySet());

                List<String> alertBody = new ArrayList<>();
                List<Alert> alerts = new ArrayList<>();
                for (Map<String, Object> record : aggregatedQueryResult) {
                    for (String header : headers) {
                        var value = record.get(header);
                        alertBody.add(value.toString());
                    }
                    createAlert(queriedScript, alerts, record);
                    System.out.println(">>>>>>>>>>>>>>> Completed the agregated Result loop <<<<<<<<<<<<<");
                }
                try {
                    //emailService.sendMail(cluster, headers, alertBody);
                    emailService.sendMail();
                    System.out.println(">>>>>>>>>>>>>>> Completed the Send mail method <<<<<<<<<<<<<<<<");
                }catch (Exception e){
                    throw new MailNotSentException("Mail sending failed..."+ e.getCause());
//                System.out.println(e);
                }
            }
        }
        System.out.println(">>>>>>>>>>>> query result : " + aggregatedQueryResult);
        return aggregatedQueryResult;
    }

    private void createAlert(Script queriedScript, List<Alert> alerts, Map<String, Object> record) {
        Alert alert = new Alert(queriedScript, AlertStatus.UNASSIGNED);
        alert.setEntryDate(LocalDateTime.parse(record.get("entry_date").toString()));
        alert.setTransactionDate(LocalDate.parse(record.get("tran_date").toString()));
        alert.setTranId(record.get("tran_id").toString());
        alert.setTranAmount((Double) record.get("tran_amt"));
        alert.setSolId((Integer) record.get("sol_id"));
        alerts.add(alert);
        alertRepository.save(alert);
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
