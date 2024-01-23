package com.plantaccion.alartapp.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.springframework.stereotype.Service;


public interface WriteEmailService {
    void sendMail() throws MessagingException;
}
