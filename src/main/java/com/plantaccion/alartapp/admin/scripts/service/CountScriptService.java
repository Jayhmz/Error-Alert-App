package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.common.repository.app.ScriptRepository;
import org.springframework.stereotype.Service;

@Service
public class CountScriptService {
    private final ScriptRepository scriptRepository;

    public CountScriptService(ScriptRepository scriptRepository) {
        this.scriptRepository = scriptRepository;
    }

    public Long countActiveScripts(){
        return scriptRepository.countOfActiveScripts();
    }
}
