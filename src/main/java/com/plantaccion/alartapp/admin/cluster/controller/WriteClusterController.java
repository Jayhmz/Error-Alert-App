package com.plantaccion.alartapp.admin.cluster.controller;

import com.plantaccion.alartapp.admin.cluster.dto.ClusterDTO;
import com.plantaccion.alartapp.admin.cluster.service.WriteClusterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/admin/cluster")
public class WriteClusterController {
    private final WriteClusterService writeClusterService;

    public WriteClusterController(WriteClusterService writeClusterService) {
        this.writeClusterService = writeClusterService;
    }

    @PostMapping
    public ResponseEntity<?> createCluster(ClusterDTO clusterDTO) {
        writeClusterService.createCluster(clusterDTO);
        return new ResponseEntity<>("Cluster created successfully.", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCluster(ClusterDTO clusterDTO) {
        writeClusterService.updateCluster(clusterDTO);
        return new ResponseEntity<>("Cluster created successfully.", HttpStatus.CREATED);
    }
}
