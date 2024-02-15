package com.plantaccion.alartapp.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingController {
    @GetMapping("/")
    @ResponseBody
    public String landingAPIController(){
        return "Welcome to alert app...";
    }
}
