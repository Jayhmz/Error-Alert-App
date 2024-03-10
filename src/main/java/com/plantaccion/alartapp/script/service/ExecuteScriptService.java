package com.plantaccion.alartapp.script.service;

import com.plantaccion.alartapp.common.model.app.Script;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface ExecuteScriptService {
    void executeQuery(Script query) throws MessagingException, IOException;
}
