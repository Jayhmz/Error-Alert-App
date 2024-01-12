package com.plantaccion.alartapp.admin.staff.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.common.model.RegionalControlHeadProfile;

import java.util.Map;

public class StaffResponse {
    @JsonProperty("id")
    private String staffId;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private String role;
    @JsonProperty("profile")
    private Map<String, Object> profile;

    public StaffResponse(String staffId, String firstname, String lastname, String email, String role, Map<String, Object> profile) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.profile = profile;
    }
    public StaffResponse(String staffId, String firstname, String lastname, String email, String role) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }
}
