package com.plantaccion.alartapp.authentication.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.common.enums.Roles;

public class RegistrationResponse {
    @JsonProperty("id")
    private Long staffId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private Roles role;

    public RegistrationResponse(Long staffId, String email, Roles role) {
        this.staffId = staffId;
        this.email = email;
        this.role = role;
    }
}
