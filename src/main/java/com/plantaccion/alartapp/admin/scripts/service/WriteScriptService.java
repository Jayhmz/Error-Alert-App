package com.plantaccion.alartapp.admin.scripts.service;

import com.plantaccion.alartapp.common.dto.ScriptDTO;
import com.plantaccion.alartapp.admin.scripts.response.ScriptResponse;

public interface WriteScriptService {
    ScriptResponse createScript(ScriptDTO scriptDTO);
    ScriptResponse editScript(Long id, ScriptDTO scriptDTO);
}
