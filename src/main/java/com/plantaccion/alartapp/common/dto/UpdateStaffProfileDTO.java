package com.plantaccion.alartapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.authentication.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;

public class UpdateStaffProfileDTO {
    @JsonProperty("staffId")
    @NotNull(message = "staffId cannot be null")
    private Long staffId;
    @JsonProperty("role")
    @NotNull(message = "No role selected")
    private String role;
    @JsonProperty("cluster")
    @NotNull(message = "Enter a valid cluster")
    private String cluster;
    @JsonProperty("supervisor")
    private Long supervisor;

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

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public Long getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Long supervisor) {
        this.supervisor = supervisor;
    }
}
