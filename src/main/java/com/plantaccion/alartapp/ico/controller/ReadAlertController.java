package com.plantaccion.alartapp.ico.controller;

import com.plantaccion.alartapp.ico.service.ReadAlertsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/ico")
public class ReadAlertController {
    private final ReadAlertsService readAlertsService;

    public ReadAlertController(ReadAlertsService readAlertsService) {
        this.readAlertsService = readAlertsService;
    }

    @GetMapping("/alerts")
    public ResponseEntity<?> getAllAlertsByCluster(){
        return new ResponseEntity<>(readAlertsService.getAllAlertsByCluster(), HttpStatus.OK);
    }
    @GetMapping("/my-alerts")
    public ResponseEntity<?> getAllAlertByICO(){
        return new ResponseEntity<>(readAlertsService.getAllAlertByICO(), HttpStatus.OK);
    }
}
