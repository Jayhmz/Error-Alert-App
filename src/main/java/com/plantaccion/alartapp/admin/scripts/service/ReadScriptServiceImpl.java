package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.admin.scripts.response.ScriptResponse;
import com.plantaccion.alartapp.common.model.app.Script;
import com.plantaccion.alartapp.common.repository.app.ScriptRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReadScriptServiceImpl implements ReadScriptService{

    private final ScriptRepository scriptRepository;

    public ReadScriptServiceImpl(ScriptRepository scriptRepository) {
        this.scriptRepository = scriptRepository;
    }

    @Override
    public List<ScriptResponse> getAllScripts() {
        var scriptList = scriptRepository.findAll();
        List<ScriptResponse> scriptResponses = new ArrayList<>();
        for(Script script : scriptList){
            Map<String, Object> user = new HashMap<>();
            user.put("id", script.getCreatedBy().getId());
            user.put("email", script.getCreatedBy().getEmail());

            scriptResponses.add(new ScriptResponse(script.getId(), script.getTitle(), script.getBody(), user));
        }
        return scriptResponses;
    }

    @Override
    public ScriptResponse getScriptById(Long id) {
        System.out.println("inside the read script service");
        var script = Optional.of(scriptRepository.findById(id)).get();
        Map<String, Object> user = new HashMap<>();
        user.put("id", script.get().getCreatedBy().getId());
        user.put("email", script.get().getCreatedBy().getEmail());
        return new ScriptResponse(script.get().getId(), script.get().getTitle(), script.get().getBody(), user);
    }
}
