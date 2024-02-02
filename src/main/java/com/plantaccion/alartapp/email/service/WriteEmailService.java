package com.plantaccion.alartapp.email.service;

import com.plantaccion.alartapp.common.model.Cluster;
import com.plantaccion.alartapp.common.model.Script;
import jakarta.mail.MessagingException;


public interface WriteEmailService {

    void sendMail(Cluster cluster, Script queriedScript) throws MessagingException;
}
