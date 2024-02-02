package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.common.repository.AlertRepository;
import com.plantaccion.alartapp.common.repository.ICOProfileRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import com.plantaccion.alartapp.ico.response.AlertResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadAlertsServiceImpl implements ReadAlertsService {
    private final ICOProfileRepository icoProfileRepository;
    private final AlertRepository alertRepository;

    public ReadAlertsServiceImpl(ICOProfileRepository icoProfileRepository, AlertRepository alertRepository) {
        this.icoProfileRepository = icoProfileRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public List<AlertResponse> getAllAlertsByCluster() {
        var ico = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        var icoProfile = icoProfileRepository.findByStaffId(ico.getStaffId());

        var cluster = icoProfile.getOnboardedBy().getCluster();

        List<AlertResponse> responses = new ArrayList<>();
        var alerts = alertRepository.findAlertsByCluster(cluster);
        for (Alert alert : alerts) {
            responses.add(new AlertResponse(alert.getAlertId(), alert.getScript().getTitle(), alert.getResolution(),
                    alert.getSolId(), alert.getTranAmount(), alert.getTranId(), alert.getTransactionDate(), alert.getStatus())
            );
        }
        return responses;
    }

    @Override
    public List<AlertResponse> getAllAlertByICO() {
        var ico = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        List<AlertResponse> responses = new ArrayList<>();

        var alerts = alertRepository.findAlertsByResolvedBy(ico);

        for (Alert alert : alerts) {
            responses.add(new AlertResponse(alert.getAlertId(), alert.getScript().getTitle(), alert.getResolution(),
                    alert.getSolId(), alert.getTranAmount(), alert.getTranId(), alert.getTransactionDate(), alert.getStatus())
            );
        }
        return responses;
    }
}
