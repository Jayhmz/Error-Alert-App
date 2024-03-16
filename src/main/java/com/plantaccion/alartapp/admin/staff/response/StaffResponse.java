package com.plantaccion.alartapp.admin.staff.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class StaffResponse {
    @JsonProperty("id")
    private Long staffId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private String role;
    @JsonProperty("profile")
    private Map<String, Object> profile;

    public StaffResponse(Long staffId, String email, String role,
                         Map<String, Object> profile) {
        this.staffId = staffId;
        this.email = email;
        this.role = role;
        this.profile = profile;
    }
    public StaffResponse(Long staffId, String email) {
        this.staffId = staffId;
        this.email = email;
    }
}
