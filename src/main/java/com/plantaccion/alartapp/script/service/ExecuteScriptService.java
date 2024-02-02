package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.model.Script;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExecuteScriptService {
    void executeQuery(Script query) throws MessagingException, IOException;
}
