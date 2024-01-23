package com.plantaccion.alartapp.authentication.controller;

import com.plantaccion.alartapp.authentication.dto.SignInDTO;
import com.plantaccion.alartapp.authentication.jwt.JWTService;
import com.plantaccion.alartapp.authentication.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AuthenticationController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/")
    @ResponseBody
    public String freeHomepage(){
        return "this is free homepage";
    }
    @GetMapping("/home")
    @ResponseBody
    public String homepage(){
        return "this is secured homepage";
    }
    @PostMapping(value = "/api/v1/users/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String login(@RequestBody SignInDTO signInDTO) {
       return appUserService.authenticate(signInDTO);
    }

    @GetMapping(value = "/api/v1/users/login/google")
    public RedirectView loginWithGoogle() {
        return new RedirectView("/oauth2/authorization/google");
    }
}
