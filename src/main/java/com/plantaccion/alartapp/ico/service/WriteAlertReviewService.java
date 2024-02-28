package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.ico.dto.ResolutionDTO;

public interface WriteAlertReviewService {
    boolean assignAlertToUser(String alertId);
    boolean submitAlertReview(ResolutionDTO resolution, String id);
    boolean updateAlertReview(ResolutionDTO resolution, String id);
}
