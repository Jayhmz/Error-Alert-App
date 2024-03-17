package com.plantaccion.alartapp.cluster.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ClusterResponse {
    @JsonProperty("cluster")
    private String cluster;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }
}
