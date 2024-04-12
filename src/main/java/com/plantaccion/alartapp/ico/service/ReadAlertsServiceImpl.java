package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.common.model.app.Alert;
import com.plantaccion.alartapp.common.repository.app.AlertRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.NoContentException;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import com.plantaccion.alartapp.ico.response.AlertResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadAlertsServiceImpl implements ReadAlertsService {
    private final ICOProfileRepository icoProfileRepository;
    private final AlertRepository alertRepository;
    private final ZCHProfileRepository rCHProfileRepository;

    public ReadAlertsServiceImpl(ICOProfileRepository icoProfileRepository, AlertRepository alertRepository,
                                 ZCHProfileRepository rCHProfileRepository) {
        this.icoProfileRepository = icoProfileRepository;
        this.alertRepository = alertRepository;
        this.rCHProfileRepository = rCHProfileRepository;
    }

    public Page<AlertResponse> getAllAlertsByCluster(Pageable pageable) {
        var ico = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var icoProfile = icoProfileRepository.findByStaffId(ico.getStaffId());
        var cluster = icoProfile.getSupervisor().getCluster();

        Page<Alert> alerts = alertRepository.findAlertsByCluster(cluster, pageable);

        if (!alerts.hasContent()) {
            throw new NoContentException("No more existing alerts");
        }
        return alerts.map(this::mapToAlertResponse);
    }


    @Override
    public Page<AlertResponse> getAllAlertsReviewedByICO(Pageable pageable) {
        var ico = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        List<AlertResponse> responses = new ArrayList<>();
        var alerts = alertRepository.findAlertsByResolvedByAndStatusResolved(ico, pageable);
        return alerts.map(this::mapToAlertResponse);
    }


    @Override
    public Page<AlertResponse> getAllPendingAlertsByICO(Pageable pageable) {
        var ico = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var alerts = alertRepository.findAlertsByResolvedByAndStatusPending(ico, pageable);
        return alerts.map(this::mapToAlertResponse);
    }

    private AlertResponse mapToAlertResponse(Alert alert) {
        return new AlertResponse(
                alert.getAlertId(),
//                alert.getScript().getTitle(),
                alert.getScript(),
                alert.getResolution(),
                alert.getSolId(),
                alert.getTranAmount(),
                alert.getTranId(),
                alert.getTransactionDate(),
                alert.getStatus(),
                alert.getAccountNo(),
                alert.getProcessorId()
        );
    }

    @Override
    public String getAuthenticatedUserCluster() {
        var user = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var icoProfile = icoProfileRepository.findByStaffId(user.getStaffId());
        return icoProfile.getSupervisor().getCluster().getName();
    }

}
