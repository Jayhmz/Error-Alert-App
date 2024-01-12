package com.plantaccion.alartapp.authentication.service;

import com.plantaccion.alartapp.authentication.dto.RegistrationDTO;
import com.plantaccion.alartapp.authentication.dto.SignInDTO;
import com.plantaccion.alartapp.authentication.response.RegistrationResponse;

public interface AppUserService {
    RegistrationResponse createUser(RegistrationDTO registrationDTO);

    String authenticate(SignInDTO signInDTO);
}
