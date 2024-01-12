package com.plantaccion.alartapp.admin.scripts.controller;

import com.plantaccion.alartapp.common.dto.ScriptDTO;
import com.plantaccion.alartapp.admin.scripts.service.ReadScriptService;
import com.plantaccion.alartapp.admin.scripts.service.WriteScriptService;
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
public class WriteScriptsController {
    private final WriteScriptService writeScriptService;
    private final ReadScriptService readScriptService;

    public WriteScriptsController(WriteScriptService writeScriptService, ReadScriptService readScriptService) {
        this.writeScriptService = writeScriptService;
        this.readScriptService = readScriptService;
    }

    @PostMapping(value = "/scripts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createScript(@Valid @RequestBody ScriptDTO scriptDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeScriptService.createScript(scriptDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/scripts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editScript(@Valid @PathVariable("id") Long id, @RequestBody ScriptDTO scriptDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(writeScriptService.editScript(id,scriptDTO), HttpStatus.OK);
    }


}

