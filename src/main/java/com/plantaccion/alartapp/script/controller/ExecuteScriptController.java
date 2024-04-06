package com.plantaccion.alartapp.script.controller;

import com.plantaccion.alartapp.common.model.app.Script;
import com.plantaccion.alartapp.common.repository.app.ScriptRepository;
import com.plantaccion.alartapp.exception.NoContentException;
import com.plantaccion.alartapp.script.service.ScriptScheduler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/app/v1/admin")
public class ExecuteScriptController {

    private final ScriptScheduler scriptScheduler;
    private final ScriptRepository scriptRepository;

    public ExecuteScriptController(ScriptScheduler scriptScheduler, ScriptRepository scriptRepository) {
        this.scriptScheduler = scriptScheduler;
        this.scriptRepository = scriptRepository;
    }

    @GetMapping("/start-script/{id}")
    public ResponseEntity<?> runScript(@PathVariable("id") Long id) {
        var script = scriptRepository.findById(id)
                .orElseThrow(() -> new NoContentException("script not found"));
        if (!script.isActive()) {
            script.setActive(true);
            scriptRepository.save(script);
        }
        scriptScheduler.startScheduler(script);
        return new ResponseEntity<>("script is running", HttpStatus.OK);
    }

    @GetMapping("/stop-script/{id}")
    public ResponseEntity<?> stopScript(@PathVariable("id") Long id) {
        var script = scriptRepository.findById(id)
                .orElseThrow(() -> new NoContentException("script not found"));
        if (script.isActive()) {
            script.setActive(false);
            scriptRepository.save(script);
            scriptScheduler.stopScheduler(script);
        }
        return new ResponseEntity<>("script stopped", HttpStatus.OK);
    }
}