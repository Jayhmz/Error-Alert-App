package com.plantaccion.alartapp.cluster.controller;

import com.plantaccion.alartapp.admin.staff.service.ReadStaffService;
import com.plantaccion.alartapp.cluster.service.ReadClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/clusters")
public class ReadClusterController {
    @Autowired
    private ReadClusterService readClusterService;
    @GetMapping
    public ResponseEntity<?> getAllClusters(){
        return new ResponseEntity<>(readClusterService.getAllClusters(), HttpStatus.OK);
    }
}
