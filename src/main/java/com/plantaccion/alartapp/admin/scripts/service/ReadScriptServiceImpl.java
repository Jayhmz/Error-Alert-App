package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.admin.scripts.response.ScriptResponse;
import com.plantaccion.alartapp.common.model.app.Script;
import com.plantaccion.alartapp.common.repository.app.ScriptRepository;
import com.plantaccion.alartapp.exception.NoContentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReadScriptServiceImpl implements ReadScriptService{

    private final ScriptRepository scriptRepository;

    public ReadScriptServiceImpl(ScriptRepository scriptRepository) {
        this.scriptRepository = scriptRepository;
    }

    @Override
    public Page<ScriptResponse> getAllScripts(Pageable pageable) {
        var scriptList = scriptRepository.findAllScripts(pageable);
        if (scriptList.getTotalElements() < 1){
            throw new NoContentException("No script created.");
        }
        return scriptList.map(this::mapToScriptResponse);
    }

    private ScriptResponse mapToScriptResponse(Script script){
        return new ScriptResponse(script.getId(), script.getTitle(), script.getBody(), script.isActive());
    }

    @Override
    public ScriptResponse getScriptById(Long id) {
        System.out.println("inside the read script service");
        var script = Optional.of(scriptRepository.findById(id))
                .orElseThrow(()-> new NoContentException("script does not exist"));
        Map<String, Object> user = new HashMap<>();
        user.put("id", script.get().getCreatedBy().getId());
        user.put("email", script.get().getCreatedBy().getEmail());
        return new ScriptResponse(script.get().getId(), script.get().getTitle(),
                script.get().getBody(),script.get().isActive(), user);
    }
}
