package com.plantaccion.alartapp.cluster.ico.service;

import com.plantaccion.alartapp.cluster.ico.dto.ResolutionDTO;

public interface WriteAlertReviewService {
    boolean assignAlertToUser(String alertId);
    boolean unAssignAlertToUser(String alertId);
    boolean submitAlertReview(ResolutionDTO resolution, String id);
    boolean updateAlertReview(ResolutionDTO resolution, String id);
}
