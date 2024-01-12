package com.plantaccion.alartapp.script.controller;

import com.plantaccion.alartapp.script.service.ScriptScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/admin")
public class ExecuteScriptController {

    private final ScriptScheduler scriptScheduler;

    public ExecuteScriptController(ScriptScheduler scriptScheduler) {
        this.scriptScheduler = scriptScheduler;
    }

    @GetMapping("/start-script/{id}")
    public String runScript(@PathVariable("id") Long id) {
        scriptScheduler.startScheduler(id);
        return "script is running";
    }

    @GetMapping("/stop-script/{id}")
    public String stopScript(@PathVariable("id") Long id) {
           scriptScheduler.stopScheduler(id);
        return "script stopped";
    }
}