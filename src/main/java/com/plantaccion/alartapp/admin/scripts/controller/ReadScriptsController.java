package com.plantaccion.alartapp.admin.scripts.controller;

import com.plantaccion.alartapp.admin.scripts.service.ReadScriptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/admin")
public class ReadScriptsController {
    private final ReadScriptService readScriptService;

    public ReadScriptsController(ReadScriptService readScriptService) {
        this.readScriptService = readScriptService;
    }

    @GetMapping("/scripts")
    public ResponseEntity<?> getAllScripts(){
        return new ResponseEntity<>(readScriptService.getAllScripts(), HttpStatus.OK);
    }

    @GetMapping("/scripts/{id}")
    public ResponseEntity<?> getOneScript(@PathVariable("id") Long id){
        return new ResponseEntity<>(readScriptService.getScriptById(id), HttpStatus.OK);
    }
}
