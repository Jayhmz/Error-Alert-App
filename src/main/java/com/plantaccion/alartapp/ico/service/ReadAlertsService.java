package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.ico.response.AlertResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadAlertsService {
    Page<AlertResponse> getAllAlertsByCluster(Pageable pageable);
    Page<AlertResponse> getAllAlertsReviewedByICO(Pageable pageable);
    Page<AlertResponse> getAllPendingAlertsByICO(Pageable pageable);
}
