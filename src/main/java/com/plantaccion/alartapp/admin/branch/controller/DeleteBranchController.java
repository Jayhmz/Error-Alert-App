package com.plantaccion.alartapp.admin.branch.controller;

import com.plantaccion.alartapp.admin.branch.service.DeleteBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/admin/branch")
public class DeleteBranchController {
    private final DeleteBranchService deleteBranchService;

    public DeleteBranchController(DeleteBranchService deleteBranchService) {
        this.deleteBranchService = deleteBranchService;
    }

    @DeleteMapping("/{solId}")
    public ResponseEntity<?> deleteBranch(@PathVariable("solId") String solId){
        deleteBranchService.deleteBranch(solId);
        return new ResponseEntity<>("Branch deleted successfully", HttpStatus.OK);
    }
}
