package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.model.app.Script;
import com.plantaccion.alartapp.common.repository.app.ScriptRepository;
import com.plantaccion.alartapp.exception.ScriptNotFoundException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ScriptScheduler {
    private final Object lock = new Object();
    private final ScriptRepository scriptRepository;
    private final ExecuteScriptService scriptService;
    private final List<Script> scriptIdList = new ArrayList<>();

    public ScriptScheduler(ScriptRepository scriptRepository, ExecuteScriptService scriptService) {
        this.scriptRepository = scriptRepository;
        this.scriptService = scriptService;
    }

    public void startScheduler(Script script) {
        synchronized (lock) {
            if (!scriptIdList.contains(script)) {
                scriptIdList.add(script);
            }
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void processScript() throws MessagingException, IOException {
        synchronized (lock) {
            for (Script script : scriptIdList) {
                var today = LocalDateTime.now();
                String[] runDays = {"SATURDAY", "SUNDAY"};
                for (String day : runDays) {
                    if (today.getDayOfWeek() != DayOfWeek.valueOf(day.toUpperCase())) {
                        scriptService.executeQuery(script);
                    }
                }
            }
        }
    }

    private Script getScript(Long scriptId) {
        var script = scriptRepository.findById(scriptId)
                .orElseThrow(() -> new ScriptNotFoundException("Script does not exist in our record"));
        return script;
    }

    public void stopScheduler(Script script) {
        for(Script s : scriptIdList){
            if (s.getId() == script.getId()){
                scriptIdList.remove(s);
            }
        }
    }
}
