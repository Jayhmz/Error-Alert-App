package com.plantaccion.alartapp.admin.cluster.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class ClusterDTO {
    @JsonProperty("cluster_name")
    @NotNull(message = "Cluster name cannot be null")
    private String clusterName;

    @JsonProperty("state")
    @NotNull(message = "Select a state for the cluster")
    private String state;
    @JsonProperty("region")
    @NotNull(message = "Select a region for the cluster")
    private String region;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
