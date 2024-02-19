package com.plantaccion.alartapp.admin.alert.service;

import com.plantaccion.alartapp.common.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CountsOfAlertServiceImpl implements CountAlertService {
    private final AlertRepository alertRepository;

    public CountsOfAlertServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Long countOfAllAlerts() {
        return alertRepository.count();
    }

    @Override
    public Long countOfAlertsReviewed() {
        return alertRepository.countOfAlertsReviewedByYear(LocalDate.now().getYear());
    }

    @Override
    public Long countOfAlertsReviewedByMonth() {
        return alertRepository.countOfAlertsReviewedByMonth(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    @Override
    public List<Object[]> countOfAlertsByRegion() {
        return alertRepository.countAlertsByRegion();
    }

    @Override
    public List<Object[]> countOfAlertsByRegionByMonth() {
        return alertRepository.countAlertsByRegionByMonth(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    @Override
    public Long countOfAlertsByYear() {
        return alertRepository.countAlertsByYear(LocalDate.now().getYear());
    }

    @Override
    public Long countOfAlertsByMonth() {
        return alertRepository.countAlertsByMonthAndYear(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    @Override
    public int percentageOfAlertsTaken() {
        int alertsTaken = Math.toIntExact(alertRepository.countAlertsTakenByMonth(LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        int totalAlerts = Math.toIntExact(countOfAlertsByMonth());
        if (totalAlerts == 0) {
            return 0;
        }
        double percentage = ((double) alertsTaken / totalAlerts) * 100;
        return (int) Math.round(percentage);
    }

    @Override
    public int percentageOfAlertsReviewed() {
        int alertsReviewedByMonth = Math.toIntExact(countOfAlertsReviewedByMonth());
        int totalAlerts = Math.toIntExact(countOfAlertsByMonth());
        if (totalAlerts == 0){
            return 0;
        }
        double percentage = ((double) alertsReviewedByMonth / totalAlerts) * 100;
        return (int) Math.round(percentage);
    }

    @Override
    public List<Object[]> mostOccurringAlertGroup() {
        var mostOccurringAlert = alertRepository.findMostOccurringAlert();
        if (!mostOccurringAlert.isEmpty()){
            return mostOccurringAlert;
        }
        return null;
    }

}
