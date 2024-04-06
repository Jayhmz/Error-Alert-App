package com.plantaccion.alartapp.admin.cluster.controller;

import com.plantaccion.alartapp.admin.cluster.dto.ClusterDTO;
import com.plantaccion.alartapp.admin.cluster.service.WriteClusterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/v1/admin/cluster")
public class WriteClusterController {
    private final WriteClusterService writeClusterService;

    public WriteClusterController(WriteClusterService writeClusterService) {
        this.writeClusterService = writeClusterService;
    }

    @PostMapping
    public ResponseEntity<?> createCluster(@Valid @RequestBody ClusterDTO clusterDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        writeClusterService.createCluster(clusterDTO);
        return new ResponseEntity<>("Cluster created successfully.", HttpStatus.CREATED);
    }

    @PutMapping("/{cluster}")
    public ResponseEntity<?> updateCluster(@PathVariable("cluster") String cluster,@Valid @RequestBody ClusterDTO clusterDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        writeClusterService.updateCluster(cluster, clusterDTO);
        return new ResponseEntity<>("Cluster created successfully.", HttpStatus.CREATED);
    }
}
