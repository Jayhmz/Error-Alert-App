package com.plantaccion.alartapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;

public class StaffProfileDTO {
    @JsonProperty("staffId")
    @NotNull(message = "staffId cannot be null")
    private Long staffId;
    @JsonProperty("role")
    @NotNull(message = "No role selected")
    private String role;
    @JsonProperty("email")
    @ValidEmail(message = "Enter a valid email")
    private String email;
    @JsonProperty("cluster")
    private String cluster;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

}
