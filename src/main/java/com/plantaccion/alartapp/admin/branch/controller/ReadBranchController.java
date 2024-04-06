package com.plantaccion.alartapp.admin.branch.controller;

import com.plantaccion.alartapp.admin.branch.service.ReadBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/admin")
public class ReadBranchController {
    private final ReadBranchService readBranchService;

    public ReadBranchController(ReadBranchService readBranchService) {
        this.readBranchService = readBranchService;
    }

    @GetMapping("/branches/cluster/{id}")
    public ResponseEntity<?> getAllBranchesByCluster(@PathVariable("id") Long clusterId){
        return new ResponseEntity<>(readBranchService.getBranchesPerCluster(clusterId), HttpStatus.OK);
    }
    @GetMapping("/branch/{id}")
    public ResponseEntity<?> getAllBranchBySolId(@PathVariable("id") String solId){
        return new ResponseEntity<>(readBranchService.getBranchBySolId(solId), HttpStatus.OK);
    }
}
