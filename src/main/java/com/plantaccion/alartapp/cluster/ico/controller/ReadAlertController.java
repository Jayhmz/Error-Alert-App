package com.plantaccion.alartapp.cluster.ico.controller;

import com.plantaccion.alartapp.cluster.ico.response.AlertResponse;
import com.plantaccion.alartapp.cluster.ico.service.ReadAlertsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/v1/ico")
public class ReadAlertController {
    private final ReadAlertsService readAlertsService;

    public ReadAlertController(ReadAlertsService readAlertsService) {
        this.readAlertsService = readAlertsService;
    }

    @GetMapping("/alerts")
    public ResponseEntity<?> getAllAlertsByCluster(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<AlertResponse> alertResponses = readAlertsService.getAllAlertsByCluster(PageRequest.of(page, size));
            return new ResponseEntity<>(alertResponses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving alerts: {}", e.getMessage());
            return new ResponseEntity<>("Empty alert list", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/pending-alerts")
    public ResponseEntity<?> getAllPendingAlertsByICO(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {

        try {
            return new ResponseEntity<>(readAlertsService.getAllPendingAlertsByICO(PageRequest.of(page, size)), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving alerts: {}", e.getMessage());
            return new ResponseEntity<>("Empty alert list", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/reviewed-alerts")
    public ResponseEntity<?> getAllReviewedAlertsByICO(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {

        try {
            return new ResponseEntity<>(readAlertsService.getAllAlertsReviewedByICO(PageRequest.of(page, size)), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving alerts: {}", e.getMessage());
            return new ResponseEntity<>("Empty alert list", HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/get-user-cluster")
    public ResponseEntity<?> getUserCluster() {
        return new ResponseEntity<>(readAlertsService.getAuthenticatedUserCluster(), HttpStatus.OK);
    }



}
