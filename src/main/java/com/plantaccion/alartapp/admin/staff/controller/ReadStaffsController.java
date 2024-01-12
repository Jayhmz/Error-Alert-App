package com.plantaccion.alartapp.admin.staff.controller;

import com.plantaccion.alartapp.admin.staff.service.ReadStaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/admin")
public class ReadStaffsController {
    private final ReadStaffService readStaffService;

    public ReadStaffsController(ReadStaffService readStaffService) {
        this.readStaffService = readStaffService;
    }

    @GetMapping("/staffs")
    public ResponseEntity<?> getAllStaffs(){
        return new ResponseEntity<>(readStaffService.getAllStaff(), HttpStatus.OK);
    }
    @GetMapping("/staffs/{id}")
    private ResponseEntity<?> getOneStaff(@PathVariable("id") String id){
        return new ResponseEntity<>(readStaffService.getOneStaff(id), HttpStatus.OK);
    }
}
