package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.common.repository.app.AlertRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CountAlertByClusterServiceImpl implements CountAlertByClusterService{
    private final AlertRepository alertRepository;
    private final ICOProfileRepository icoProfileRepository;

    public CountAlertByClusterServiceImpl(AlertRepository alertRepository, ICOProfileRepository icoProfileRepository) {
        this.alertRepository = alertRepository;
        this.icoProfileRepository = icoProfileRepository;
    }

    @Override
    public int countAlertByCluster() {
        var user = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var ico = icoProfileRepository.findByStaffId(user.getStaffId());
        return alertRepository.countAlertByCluster(ico.getSupervisor().getCluster());
    }
    @Override
    public int countAlertReviewedByICO() {
        var user = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var ico = icoProfileRepository.findByStaffId(user.getStaffId());
        return alertRepository.countAlertReviewedByICO(ico.getSupervisor().getCluster(), ico.getIcoStaff());
    }
}
