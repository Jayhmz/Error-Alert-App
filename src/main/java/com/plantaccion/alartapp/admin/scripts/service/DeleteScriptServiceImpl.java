package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.common.repository.app.ScriptRepository;
import com.plantaccion.alartapp.exception.ScriptNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteScriptServiceImpl implements DeleteScriptService {
    private final ScriptRepository scriptRepository;

    public DeleteScriptServiceImpl(ScriptRepository scriptRepository) {
        this.scriptRepository = scriptRepository;
    }

    @Override
    public void softDeleteById(Long id) {
        var script = scriptRepository.findById(id)
                .orElseThrow(() -> new ScriptNotFoundException("Script does not exist"));
            script.setDisabled(true);
            scriptRepository.save(script);
    }

    @Override
    public void activateScriptById(Long id) {
        var script = scriptRepository.findById(id)
                .orElseThrow(() -> new ScriptNotFoundException("Script does not exist"));
        script.setDisabled(false);
        scriptRepository.save(script);
    }

    @Override
    public void hardDeleteById(Long id) {
        var script = scriptRepository.findById(id)
                .orElseThrow(() -> new ScriptNotFoundException("Script does not exist"));
        scriptRepository.deleteById(script.getId());
    }
}
