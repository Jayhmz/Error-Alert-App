package com.plantaccion.alartapp.email.service;

import com.plantaccion.alartapp.common.model.app.Alert;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.model.app.Script;
import jakarta.mail.MessagingException;

import java.util.List;


public interface WriteEmailService {

    void sendMail(Cluster cluster, List<Alert> alerts) throws MessagingException;
}
