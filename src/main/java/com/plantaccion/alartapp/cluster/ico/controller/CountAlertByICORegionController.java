package com.plantaccion.alartapp.cluster.ico.controller;

import com.plantaccion.alartapp.cluster.ico.service.CountAlertByClusterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/ico")
public class CountAlertByICORegionController {
    private final CountAlertByClusterService countAlertByClusterService;

    public CountAlertByICORegionController(CountAlertByClusterService countAlertByClusterService) {
        this.countAlertByClusterService = countAlertByClusterService;
    }

    @GetMapping("/count-alerts-in-zone")
    private ResponseEntity<?> countAlertByIcoCluster(){
        return new ResponseEntity<>(countAlertByClusterService.countAlertByCluster(), HttpStatus.OK);
    }

    @GetMapping("/count-alerts-resolved-by-ico")
    private ResponseEntity<?> countAlertResolvedByIco(){
        return new ResponseEntity<>(countAlertByClusterService.countAlertReviewedByICO(), HttpStatus.OK);
    }
}
