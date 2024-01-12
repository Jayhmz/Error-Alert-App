package com.plantaccion.alartapp.admin.scripts.service;

public interface DeleteScriptService {
    void softDeleteById(Long id);
    void activateScriptById(Long id);
    void hardDeleteById(Long id);
}
