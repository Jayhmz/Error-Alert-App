package com.plantaccion.alartapp.ico.response;

import com.plantaccion.alartapp.common.enums.AlertStatus;

import java.time.LocalDate;

public class AlertResponse {
    private String alertId;
    private String scriptCategory;
    private String resolution;
    private int sol_id;
    private double tran_amt;
    private String tran_id;
    private LocalDate tran_date;
    private AlertStatus status;

    private String accountNo;

    private String processorId;

    public AlertResponse(String alertId, String scriptCategory, String resolution, int sol_id, double tran_amt,
                         String tran_id, LocalDate tran_date, AlertStatus status, String accountNo, String processorId) {
        this.alertId = alertId;
        this.scriptCategory = scriptCategory;
        this.resolution = resolution;
        this.sol_id = sol_id;
        this.tran_amt = tran_amt;
        this.tran_id = tran_id;
        this.tran_date = tran_date;
        this.status = status;
        this.accountNo = accountNo;
        this.processorId = processorId;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getScriptCategory() {
        return scriptCategory;
    }

    public void setScriptCategory(String scriptCategory) {
        this.scriptCategory = scriptCategory;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getSol_id() {
        return sol_id;
    }

    public void setSol_id(int sol_id) {
        this.sol_id = sol_id;
    }

    public double getTran_amt() {
        return tran_amt;
    }

    public void setTran_amt(double tran_amt) {
        this.tran_amt = tran_amt;
    }

    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }

    public LocalDate getTran_date() {
        return tran_date;
    }

    public void setTran_date(LocalDate tran_date) {
        this.tran_date = tran_date;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getProcessorId() {
        return processorId;
    }

    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }
}
