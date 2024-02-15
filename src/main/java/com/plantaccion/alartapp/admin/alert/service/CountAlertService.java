package com.plantaccion.alartapp.admin.alert.service;

import java.util.List;

public interface CountAlertService {
    Long countOfAllAlerts();
    Long countOfAlertsReviewed();
    Long countOfAlertsReviewedByMonth();
    List<Object[]> countOfAlertsByRegion();
    List<Object[]> countOfAlertsByRegionByMonth();
    Long countOfAlertsByYear();
    Long countOfAlertsByMonth();
    int percentageOfAlertsTaken();
    int percentageOfAlertsReviewed();

}
