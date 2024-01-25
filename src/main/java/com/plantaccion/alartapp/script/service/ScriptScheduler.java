package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.repository.ScriptRepository;
import com.plantaccion.alartapp.exception.ScriptNotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ScriptScheduler {
    private final Object lock = new Object();
    private final ScriptRepository scriptRepository;
    private final ExecuteScriptService scriptService;
    private List<Long> scriptIdList = new ArrayList<>();

    public ScriptScheduler(ScriptRepository scriptRepository, ExecuteScriptService scriptService) {
        this.scriptRepository = scriptRepository;
        this.scriptService = scriptService;
    }

    public void startScheduler(Long scriptId) {
        synchronized (lock) {
            scriptIdList.add(scriptId);
        }
    }

    @Scheduled(fixedDelay = 50000)
    public void processScript() throws MessagingException, IOException {
        synchronized (lock) {
            Iterator<Long> iterator = scriptIdList.iterator();
            while (iterator.hasNext()) {
                var script = scriptRepository.findById(iterator.next())
                        .orElseThrow(() -> new ScriptNotFoundException("Script does not exist in our record"));

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

    public void stopScheduler(Long id) {
        synchronized (lock) {
            if (scriptIdList.contains(id)) {
                scriptIdList.remove(id);
            }
        }
    }
}
