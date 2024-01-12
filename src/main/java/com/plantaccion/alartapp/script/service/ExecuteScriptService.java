package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.model.Script;

import java.util.List;
import java.util.Map;

public interface ExecuteScriptService {
    List<Map<String,Object>> executeQuery(Script query);
}
