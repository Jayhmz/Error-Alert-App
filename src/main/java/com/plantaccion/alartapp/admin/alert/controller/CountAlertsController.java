package com.plantaccion.alartapp.admin.alert.controller;

import com.plantaccion.alartapp.admin.alert.service.CountAlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/admin")
public class CountAlertsController {
    private final CountAlertService countAlertService;

    public CountAlertsController(CountAlertService countAlertService) {
        this.countAlertService = countAlertService;
    }

    @GetMapping("/total-alert-count")
    public Long countTotalAlert(){
        return countAlertService.countOfAllAlerts();
    }
    @GetMapping("/total-annual-alerts")
    public Long totalYearlyAlert(){
        return countAlertService.countOfAlertsByYear();
    }
    @GetMapping("/total-monthly-alerts")
    public Long totalMonthlyAlerts(){
        return countAlertService.countOfAlertsByMonth();
    }
    @GetMapping("/total-monthly-reviewed-alerts")
    public Long totalMonthlyReviewedAlert(){
        return countAlertService.countOfAlertsReviewedByMonth();
    }
    @GetMapping("/total-monthly-alerts-by-region")
    public ResponseEntity<?> totalMonthlyAlertsByRegion(){
        return new ResponseEntity<>(countAlertService.countOfAlertsByRegionByMonth(), HttpStatus.OK);
    }
    @GetMapping("/percentage-monthly-alerts-taken")
    public ResponseEntity<?> percentageMonthlyAlertsTaken(){
        return new ResponseEntity<>(countAlertService.percentageOfAlertsTaken(), HttpStatus.OK);
    }
    @GetMapping("/percentage-monthly-alerts-reviewed")
    public ResponseEntity<?> percentageMonthlyAlertsReviewed(){
        return new ResponseEntity<>(countAlertService.percentageOfAlertsReviewed(), HttpStatus.OK);
    }

}
