package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.admin.scripts.response.ScriptResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadScriptService {
    Page<ScriptResponse> getAllScripts(Pageable pageable);
    ScriptResponse getScriptById(Long id);
}
