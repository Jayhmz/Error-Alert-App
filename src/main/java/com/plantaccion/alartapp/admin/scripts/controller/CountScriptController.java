package com.plantaccion.alartapp.admin.scripts.controller;

import com.plantaccion.alartapp.admin.scripts.service.CountScriptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/admin")
public class CountScriptController {
    private final CountScriptService countScriptService;

    public CountScriptController(CountScriptService countScriptService) {
        this.countScriptService = countScriptService;
    }

    @GetMapping("/total-active-scripts")
    public ResponseEntity<Long> countOfActiveScripts(){
        return new ResponseEntity<>(countScriptService.countActiveScripts(), HttpStatus.OK);
    }
}
