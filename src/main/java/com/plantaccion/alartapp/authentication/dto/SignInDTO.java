package com.plantaccion.alartapp.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.authentication.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;

public class SignInDTO {
    @JsonProperty("email")
    @ValidEmail(message = "Input your registered email")
    private String email;

    @JsonProperty("password")
    @NotNull(message = "Input your password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
