package com.plantaccion.alartapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class UpdateStaffProfileDTO {
    @JsonProperty("cluster")
    @NotNull(message = "Enter a valid cluster")
    private String cluster;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }
}
