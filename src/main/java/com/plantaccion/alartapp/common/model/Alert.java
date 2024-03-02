package com.plantaccion.alartapp.common.model;

import com.plantaccion.alartapp.common.enums.AlertStatus;
import com.plantaccion.alartapp.common.utils.AlertSequenceIdGenerator;
import jakarta.persistence.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_sequence")
    @GenericGenerator(
            name = "alert_sequence",
            strategy = "com.plantaccion.alartapp.common.utils.AlertSequenceIdGenerator",
            parameters = {
                    @Parameter(name = AlertSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "Alert"),
                    @Parameter(name = AlertSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            }
    )
    @Column(name = "alert_id")
    private String alertId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "script_title", referencedColumnName = "title")
    private Script script;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "generated_on", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime generatedOn = LocalDateTime.now();

    @Column(name = "entry_date")
    private LocalDateTime entryDate;
    @Column(name = "tran_date")
    private LocalDate transactionDate;

    @Column(name = "tran_amt")
    private double tranAmount;

    @Column(name = "sol_id")
    private int solId;

    @Column(name = "tran_id")
    private String tranId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('PENDING', 'RESOLVED', 'UNASSIGNED')")
    private AlertStatus status;

    @Column(name = "is_mail_sent", nullable = false, columnDefinition = "BIT DEFAULT b'0'")
    private boolean isMailSent;

    @Column(name = "mail_sent_on")
    private LocalDateTime mailSentOn;

    @Column(name = "resolution")
    private String resolution;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "resolved_by", referencedColumnName = "staff_id")
    private AppUser resolvedBy;

    @ManyToOne
    @JoinColumn(name = "cluster", referencedColumnName = "cluster_name")
    private Cluster cluster;

    public Alert() {
        this.generatedOn = LocalDateTime.now();
    }

    public Alert(Script script, AlertStatus status) {
        this.script = script;
        this.status = status;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public boolean isMailSent() {
        return isMailSent;
    }

    public void setMailSent(boolean mailSent) {
        isMailSent = mailSent;
    }

    public LocalDateTime getMailSentOn() {
        return mailSentOn;
    }

    public void setMailSentOn(LocalDateTime mailSentOn) {
        this.mailSentOn = mailSentOn;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public AppUser getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(AppUser resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public int getSolId() {
        return solId;
    }

    public void setSolId(int solId) {
        this.solId = solId;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
