package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.common.dto.ScriptDTO;
import com.plantaccion.alartapp.admin.scripts.response.ScriptResponse;
import com.plantaccion.alartapp.common.model.Script;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
import com.plantaccion.alartapp.common.repository.ScriptRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.ScriptNotFoundException;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WriteScriptServiceImpl implements WriteScriptService {

    private final ScriptRepository repository;
    private final AppUserRepository appUserRepository;

    public WriteScriptServiceImpl(ScriptRepository repository, AppUserRepository appUserRepository) {
        this.repository = repository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional
    public ScriptResponse createScript(ScriptDTO scriptDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        Script script = new Script(scriptDTO.getTitle(), scriptDTO.getBody(), authenticatedUser);
        repository.save(script);

        //passing the user object to the payload
        Map<String, Object> user = new HashMap<>();
        user.put("id", script.getCreatedBy().getId());
        user.put("email", script.getCreatedBy().getEmail());
        user.put("firstname", script.getCreatedBy().getFirstname());
        user.put("lastname", script.getCreatedBy().getLastname());

        return new ScriptResponse(script.getId(), script.getTitle(), script.getBody(), user);
    }

    @Override
    @Transactional
    public ScriptResponse editScript(Long id, ScriptDTO scriptDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        Optional<Script> scriptResult = Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ScriptNotFoundException("Script cannot be found")));
        var script = scriptResult.get();

        script.setBody(scriptDTO.getBody());
        script.setTitle(scriptDTO.getTitle());
        script.setCreatedBy(authenticatedUser);
        repository.save(script);

        Map<String, Object> user = new HashMap<>();
        user.put("id", script.getCreatedBy().getId());
        user.put("email", script.getCreatedBy().getEmail());
        user.put("firstname", script.getCreatedBy().getFirstname());
        user.put("lastname", script.getCreatedBy().getLastname());

        return new ScriptResponse(script.getId(), script.getTitle(), script.getBody(), user);
    }
}
