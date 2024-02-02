package com.plantaccion.alartapp.rch.controller;

import com.plantaccion.alartapp.common.dto.RchDTO;
import com.plantaccion.alartapp.rch.dto.IcoDTO;
import com.plantaccion.alartapp.rch.service.WriteICOService;
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
@RequestMapping("/app/v1/rch")
public class WriteICOController {
    private final WriteICOService writeICOService;

    public WriteICOController(WriteICOService writeICOService) {
        this.writeICOService = writeICOService;
    }

    @PostMapping(value = "/staffs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStaff(@Valid @RequestBody IcoDTO staffDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeICOService.createStaff(staffDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/staffs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editStaff(@Valid @PathVariable("id") String staffId, @RequestBody IcoDTO staffDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeICOService.editStaff(staffId, staffDTO), HttpStatus.CREATED);
    }
}
