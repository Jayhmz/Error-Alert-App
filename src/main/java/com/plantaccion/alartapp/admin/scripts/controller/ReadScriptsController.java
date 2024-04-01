package com.plantaccion.alartapp.admin.scripts.controller;

import com.plantaccion.alartapp.admin.scripts.service.ReadScriptService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/admin")
public class ReadScriptsController {
    private final ReadScriptService readScriptService;

    public ReadScriptsController(ReadScriptService readScriptService) {
        this.readScriptService = readScriptService;
    }

    @GetMapping("/scripts")
    public ResponseEntity<?> getAllScripts(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size){
        return new ResponseEntity<>(readScriptService.getAllScripts(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/scripts/{id}")
    public ResponseEntity<?> getOneScript(@PathVariable("id") Long id){
        return new ResponseEntity<>(readScriptService.getScriptById(id), HttpStatus.OK);
    }
}
