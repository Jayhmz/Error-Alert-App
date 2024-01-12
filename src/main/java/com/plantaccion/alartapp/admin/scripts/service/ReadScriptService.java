package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.admin.scripts.response.ScriptResponse;

import java.util.List;

public interface ReadScriptService {
    List<ScriptResponse> getAllScripts();
    ScriptResponse getScriptById(Long id);
}
