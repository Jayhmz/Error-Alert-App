package com.plantaccion.alartapp.admin.staff.controller;

import com.plantaccion.alartapp.common.dto.StaffProfileDTO;
import com.plantaccion.alartapp.admin.staff.service.WriteStaffsService;
import com.plantaccion.alartapp.common.dto.UpdateStaffProfileDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/v1/admin")
@CrossOrigin(origins = {"https://scripting-app-frontend.vercel.app", "http://localhost:5173"})
public class WriteStaffsController {
    private final WriteStaffsService writeStaffsService;

    public WriteStaffsController(WriteStaffsService writeStaffsService) {
        this.writeStaffsService = writeStaffsService;
    }

    @PostMapping(value = "/staffs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStaff(@Valid @RequestBody StaffProfileDTO staffDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeStaffsService.createStaff(staffDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/staffs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editStaff(@Valid @PathVariable("id") Long staffId, @RequestBody UpdateStaffProfileDTO staffDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeStaffsService.editStaff(staffId, staffDTO), HttpStatus.CREATED);
    }


}
