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
            for (Branch branch : cluster.getBranches()) {
                String replacedQuery = queriedScript.getBody().replace("&branch", "'" + branch.getSolId() + "'");
                if (lastRecordedAlert != null) {
//                    String completeQuery = replacedQuery + " AND a.tran_date > TO_DATE('" + date + "', 'DD/MM/YYYY')";
                    String completeQuery = replacedQuery + " AND a.entry_date > '" + lastRecordedAlert.getEntryDate() + "'";
                    List<Map<String, Object>> queryResult = template.queryForList(completeQuery);
                    aggregatedQueryResult.addAll(queryResult);
                } else {
                    List<Map<String, Object>> queryResult = template.queryForList(replacedQuery);
                    aggregatedQueryResult.addAll(queryResult);
                }
            }
        }
        System.out.println(">>>>>>>>>>>> query result : " + aggregatedQueryResult);

        List<Map<String, Object>> result = new ArrayList<>();
        if (!aggregatedQueryResult.isEmpty()) {
            List<String> headers = new ArrayList<>();
            Map<String, Object> firstRecord = aggregatedQueryResult.get(0);
            for (String title : firstRecord.keySet()) {
                headers.add(title);
            }

            List<String> alertBody = new ArrayList<>();
            List<Alert> alerts = new ArrayList<>();
            for (Map<String, Object> record : aggregatedQueryResult) {
                for (String header : headers) {
                    var value = record.get(header);
                    alertBody.add(value.toString());
                }
                Alert alert = new Alert(queriedScript, AlertStatus.UNASSIGNED);
                alert.setEntryDate(LocalDateTime.parse(record.get("entry_date").toString()));
                alert.setTransactionDate(LocalDate.parse(record.get("tran_date").toString()));
                alert.setTranId(record.get("tran_id").toString());
                alert.setTranAmount((Double) record.get("tran_amt"));
                alert.setSolId((Integer) record.get("sol_id"));
                alerts.add(alert);
                alertRepository.save(alert);
            }
            //input the sendmail method here. sendMail(header, body, from, to, cc)
            try {
                emailService.sendMail();
                System.out.println("mail sent successfully....");
            }catch (Exception e){
//                throw new MailNotSentException("Mail sending failed..."+ e.getCause());
                System.out.println(e);
            }
//            result.addAll(aggregatedQueryResult);
//            System.out.println(">>>>>>>>>>>> Total result : " + result);
//            System.out.println(">>>>>>>>>>>> Total aggregate result  : " + aggregatedQueryResult);
//            System.out.println(">>>>>>>>>>>> Header : " + headers);
//            System.out.println(">>>>>>>>>>>> Body : " + alertBody);
        }
        return aggregatedQueryResult;
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
