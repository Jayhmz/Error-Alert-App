package com.plantaccion.alartapp.rch.controller;

import com.plantaccion.alartapp.admin.staff.service.ReadStaffService;
import com.plantaccion.alartapp.rch.service.ReadICOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/rch")
public class ReadICOController {
    private final ReadICOService readICOservice;

    public ReadICOController(ReadICOService readICOService) {
        this.readICOservice = readICOService;
    }

    @GetMapping("/staffs")
    public ResponseEntity<?> getAllStaffs(){
        return new ResponseEntity<>(readICOservice.getAllStaff(), HttpStatus.OK);
    }
    @GetMapping("/staffs/{id}")
    private ResponseEntity<?> getOneStaff(@PathVariable("id") Long id){
        return new ResponseEntity<>(readICOservice.getOneStaff(id), HttpStatus.OK);
    }
}
