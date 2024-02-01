package com.plantaccion.alartapp.ico.service;

import com.plantaccion.alartapp.ico.dto.ResolutionDTO;

public interface WriteAlertReviewService {
    boolean assignAlertToUser(String alertId);
    boolean createAlertReview(ResolutionDTO resolution, String id);
    boolean updateAlertReview(ResolutionDTO resolution, String id);
}
