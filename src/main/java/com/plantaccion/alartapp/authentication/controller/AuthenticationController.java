package com.plantaccion.alartapp.authentication.controller;

import com.plantaccion.alartapp.authentication.dto.SignInDTO;
import com.plantaccion.alartapp.authentication.jwt.JWTService;
import com.plantaccion.alartapp.authentication.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AppUserService appUserService;

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String login(@RequestBody SignInDTO signInDTO) {
       return appUserService.authenticate(signInDTO);
    }

}
