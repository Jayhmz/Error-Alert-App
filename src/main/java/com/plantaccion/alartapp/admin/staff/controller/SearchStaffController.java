package com.plantaccion.alartapp.admin.staff.controller;

import com.plantaccion.alartapp.admin.staff.dto.SearchStaffDTO;
import com.plantaccion.alartapp.admin.staff.service.SearchStaffsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/admin")
public class SearchStaffController {

    private final SearchStaffsService searchStaffsService;

    public SearchStaffController(SearchStaffsService searchStaffsService) {
        this.searchStaffsService = searchStaffsService;
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchStaff(@RequestBody SearchStaffDTO searchStaffDTO){
        return new ResponseEntity<>(searchStaffsService.searchByStaff(searchStaffDTO), HttpStatus.OK);
    }
}
