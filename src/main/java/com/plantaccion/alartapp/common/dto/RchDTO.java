package com.plantaccion.alartapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.authentication.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;

public class RchDTO {
    @JsonProperty("staffId")
    @NotNull(message = "staffId cannot be null")
    private String staffId;
    @JsonProperty("firstname")
    @NotNull(message = "firstname cannot be null")
    private String firstname;
    @JsonProperty("lastname")
    @NotNull(message = "lastname cannot be null")
    private String lastname;
    @JsonProperty("email")
    @ValidEmail(message = "Enter a valid email")
    private String email;
    @JsonProperty("cluster")
    @NotNull(message = "Enter a valid cluster")
    private String cluster;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
