package com.plantaccion.alartapp.admin.cluster.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class ClusterDTO {

    @JsonProperty("name")
    @NotNull(message = "Cluster name cannot be null")
    private String clusterName;
    @JsonProperty("region")
    @NotNull(message = "Select a region for the cluster")
    private String region;

    public ClusterDTO() {
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
