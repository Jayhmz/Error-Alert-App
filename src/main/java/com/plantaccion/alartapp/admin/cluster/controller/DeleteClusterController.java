package com.plantaccion.alartapp.admin.cluster.controller;

import com.plantaccion.alartapp.admin.cluster.dto.ClusterDTO;
import com.plantaccion.alartapp.admin.cluster.service.DeleteClusterService;
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
public class DeleteClusterController {
    private final DeleteClusterService deleteClusterService;

    public DeleteClusterController(DeleteClusterService deleteClusterService) {
        this.deleteClusterService = deleteClusterService;
    }

    @DeleteMapping("/{cluster}")
    public ResponseEntity<?> updateCluster(@PathVariable("cluster") String cluster) {
        deleteClusterService.deleteCluster(cluster);
        return new ResponseEntity<>("Cluster deleted successfully.", HttpStatus.OK);
    }
}
