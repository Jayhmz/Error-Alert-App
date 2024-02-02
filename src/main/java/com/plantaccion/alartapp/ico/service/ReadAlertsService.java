package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.ico.response.AlertResponse;

import java.util.List;

public interface ReadAlertsService {
    List<AlertResponse> getAllAlertsByCluster();
    List<AlertResponse> getAllAlertByICO();
}
