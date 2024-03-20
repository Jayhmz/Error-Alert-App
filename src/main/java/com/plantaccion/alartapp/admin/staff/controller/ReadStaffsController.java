package com.plantaccion.alartapp.admin.staff.controller;

import com.plantaccion.alartapp.admin.staff.service.ReadStaffService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/admin")
public class ReadStaffsController {
    private final ReadStaffService readStaffService;

    public ReadStaffsController(ReadStaffService readStaffService) {
        this.readStaffService = readStaffService;
    }

    @GetMapping("/staffs/zch")
    public ResponseEntity<?> getAllZCHProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return new ResponseEntity<>(readStaffService.getAllZCHs(PageRequest.of(page, size)), HttpStatus.OK);
    }
    @GetMapping("/staffs/ico")
    public ResponseEntity<?> getAllICOProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return new ResponseEntity<>(readStaffService.getAllICOs(PageRequest.of(page, size)), HttpStatus.OK);
    }
    @GetMapping("/staffs/{id}")
    public ResponseEntity<?> getOneStaffProfile(@PathVariable("id") Long id){
        return new ResponseEntity<>(readStaffService.getOneStaffProfile(id), HttpStatus.OK);
    }
    @GetMapping("/bank-staffs/{id}")
    public ResponseEntity<?> findOneBankStaff(@PathVariable("id") Long id){
        return new ResponseEntity<>(readStaffService.findOneBankStaff(id), HttpStatus.OK);
    }
}
