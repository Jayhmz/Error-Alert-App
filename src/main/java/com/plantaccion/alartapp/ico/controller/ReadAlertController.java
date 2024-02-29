package com.plantaccion.alartapp.ico.controller;

import com.plantaccion.alartapp.ico.response.AlertResponse;
import com.plantaccion.alartapp.ico.service.ReadAlertsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                                                   @RequestParam(defaultValue = "2") int size) {
        try {
            Page<AlertResponse> alertResponses = readAlertsService.getAllAlertsByCluster(PageRequest.of(page, size));
            return new ResponseEntity<>(alertResponses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving alerts: {}", e.getMessage());
            return new ResponseEntity<>("End of list",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my-alerts")
    public ResponseEntity<?> getAllAlertByICO(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "1") int size){
        return new ResponseEntity<>(readAlertsService.getAllAlertByICO(PageRequest.of(page, size)), HttpStatus.OK);
    }
}
