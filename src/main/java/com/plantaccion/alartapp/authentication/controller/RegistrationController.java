package com.plantaccion.alartapp.authentication.controller;

import com.plantaccion.alartapp.authentication.dto.RegistrationDTO;
import com.plantaccion.alartapp.authentication.jwt.JWTService;
import com.plantaccion.alartapp.authentication.response.RegistrationResponse;
import com.plantaccion.alartapp.authentication.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/users")
public class RegistrationController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private AppUserService appUserService;

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }
        RegistrationResponse user = appUserService.createUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


}
