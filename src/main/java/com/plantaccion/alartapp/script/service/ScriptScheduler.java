package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.model.Script;
import com.plantaccion.alartapp.common.repository.ScriptRepository;
import com.plantaccion.alartapp.exception.ScriptNotFoundException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class ScriptScheduler {
    private final Object lock = new Object();
    private final ScriptRepository scriptRepository;
    private final ExecuteScriptService scriptService;
    private final List<Long> scriptIdList = new ArrayList<>();

    public ScriptScheduler(ScriptRepository scriptRepository, ExecuteScriptService scriptService) {
        this.scriptRepository = scriptRepository;
        this.scriptService = scriptService;
    }

    public void startScheduler(Long scriptId) {
        synchronized (lock) {
            if(!scriptIdList.contains(scriptId)){
                scriptIdList.add(scriptId);
            }
        }
    }

    @Scheduled(fixedDelay = 30000)
    public void processScript() throws MessagingException, IOException {
        synchronized (lock) {
            for (Long scriptId : scriptIdList) {
                Script script = getScript(scriptId);

                var today = LocalDateTime.now();
                String[] runDays = {"SATURDAY", "SUNDAY"};
                for (String day : runDays) {
                    if (today.getDayOfWeek() != DayOfWeek.valueOf(day.toUpperCase())) {
                        if (!script.isActive()) {
                            script.setActive(true);
                            scriptRepository.save(script);
                            log.info("------------ inactive scripts are running fine ...");
                        }
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

    public void stopScheduler(Long id) {
        synchronized (lock) {
            if (scriptIdList.contains(id)){
                var script = getScript(id);
                script.setActive(false);
                scriptRepository.save(script);
                scriptIdList.remove(id);
            }
        }
    }
}
