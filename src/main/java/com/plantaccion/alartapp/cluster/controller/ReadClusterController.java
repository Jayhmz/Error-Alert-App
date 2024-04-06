package com.plantaccion.alartapp.cluster.controller;

import com.plantaccion.alartapp.admin.staff.service.ReadStaffService;
import com.plantaccion.alartapp.cluster.service.ReadClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/all")
    public ResponseEntity<?> getAllPaginatedClusters( @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size){
        return new ResponseEntity<>(readClusterService.getPaginatedClusters(PageRequest.of(page,size)), HttpStatus.OK);
    }
}
