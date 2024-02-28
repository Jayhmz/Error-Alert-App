package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.common.enums.AlertStatus;
import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.model.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.repository.AlertRepository;
import com.plantaccion.alartapp.common.repository.ICOProfileRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.AlertNotFoundException;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import com.plantaccion.alartapp.ico.dto.ResolutionDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.Resolution;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class WriteAlertReviewServiceImpl implements WriteAlertReviewService {
    private final AlertRepository alertRepository;
    private final ICOProfileRepository icoProfileRepository;

    public WriteAlertReviewServiceImpl(AlertRepository alertRepository, ICOProfileRepository icoProfileRepository) {
        this.alertRepository = alertRepository;
        this.icoProfileRepository = icoProfileRepository;
    }

    @Override
    public boolean assignAlertToUser(String alertId) {
        try {
            var authenticatedICO = AppUtils.getAuthenticatedUserDetails()
                    .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

            Alert alert = alertRepository.findById(alertId)
                    .orElseThrow(() -> new AlertNotFoundException("Alert id does not exist in record."));

            var ico = icoProfileRepository.findByStaffId(authenticatedICO.getStaffId());

            if (isSameCluster(ico, alert)) {
                alert.setResolvedBy(authenticatedICO);
                alert.setStatus(AlertStatus.PENDING);
                alertRepository.save(alert);
                return true;
            }
            throw new AlertNotFoundException("This alert is not for the cluster you are in.");
        } catch (Exception e) {
            log.error("An exception occurred in the WriteAlertReviewService assignAlertToUser(): ", e);
            return false;
        }
    }

    private boolean isSameCluster(InternalControlOfficerProfile ico, Alert alert) {
        return Objects.equals(ico.getOnboardedBy().getCluster(), alert.getCluster());
    }


    @Override
    public boolean submitAlertReview(ResolutionDTO resolution, String alertId) {
        try {
            var authenticatedStaff = AppUtils.getAuthenticatedUserDetails()
                    .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
            var alert = alertRepository.findById(alertId).orElseThrow(() -> new AlertNotFoundException("Alert id does not exist in record."));

            if (authenticatedStaff.equals(alert.getResolvedBy())) {
                alert.setResolution(resolution.getResolution());
                alert.setResolvedBy(authenticatedStaff);
                alert.setStatus(AlertStatus.RESOLVED);
                alertRepository.save(alert);
                return true;
            } else {
                log.warn("Unauthorized attempt to review this alert.");
                return false;
            }
        } catch (Exception e) {
            log.info("An exception occurred in the WriteAlertReviewService createReviewForAssignedAlert(): " + e.getCause());
            return false;
        }
    }

    @Override
    public boolean updateAlertReview(ResolutionDTO resolution, String alertId) {
        try {
            var authenticatedStaff = AppUtils.getAuthenticatedUserDetails()
                    .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
            var alert = alertRepository.findById(alertId).orElseThrow(() -> new AlertNotFoundException("Alert id does not exist in record."));

            if (authenticatedStaff.equals(alert.getResolvedBy())) {
                alert.setResolution(resolution.getResolution());
                alertRepository.save(alert);
                return true;
            } else {
                log.warn("Unauthorized attempt to update an alert not assigned to the user.");
                return false;
            }
        } catch (Exception e) {
            log.info("An exception occurred in the WriteAlertReviewService updateAssignedAlert(): " + e.getCause());
            return false;
        }
    }

}
