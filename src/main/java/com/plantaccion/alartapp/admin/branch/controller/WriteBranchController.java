package com.plantaccion.alartapp.admin.branch.controller;

import com.plantaccion.alartapp.admin.branch.dto.BranchDTO;
import com.plantaccion.alartapp.admin.branch.service.WriteBranchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/v1/admin/branch")
public class WriteBranchController {
    private final WriteBranchService writeBranchService;

    public WriteBranchController(WriteBranchService writeBranchService) {
        this.writeBranchService = writeBranchService;
    }

    @PostMapping
    public ResponseEntity<?> createBranch(@Valid @RequestBody BranchDTO branchDTO, BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeBranchService.createBranch(branchDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{solId}")
    public ResponseEntity<?> updateBranch(@Valid @PathVariable("solId") String solId, @RequestBody BranchDTO branchDTO, BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeBranchService.updateBranch(solId, branchDTO), HttpStatus.CREATED);
    }

}
