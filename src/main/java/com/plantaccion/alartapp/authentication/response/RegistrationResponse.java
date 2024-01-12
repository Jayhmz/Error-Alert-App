package com.plantaccion.alartapp.authentication.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.common.enums.Roles;

public class RegistrationResponse {
    @JsonProperty("id")
    private String staffId;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private Roles role;

    public RegistrationResponse(String staffId, String firstname, String lastname, String email, Roles role) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }
}
