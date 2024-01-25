package com.plantaccion.alartapp.email.service;

import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.common.model.Cluster;
import com.plantaccion.alartapp.common.model.Script;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface WriteEmailService {
    void sendMail(List<Alert> alerts) throws MessagingException, IOException;

    void sendMail(Cluster cluster, Script queriedScript) throws MessagingException;
}
