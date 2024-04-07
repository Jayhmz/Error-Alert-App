package com.plantaccion.alartapp.cluster.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ClusterResponse {
    @JsonProperty("id")
    private Long clusterId;

    @JsonProperty("cluster")
    private String cluster;

    @JsonProperty("state")
    private String state;

    @JsonProperty("region")
    private String region;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public ClusterResponse() {
    }

    public ClusterResponse(Long id, String cluster) {
        this.clusterId = id;
        this.cluster = cluster;
    }

    public ClusterResponse(Long clusterId, String cluster, String state, String region) {
        this.clusterId = clusterId;
        this.cluster = cluster;
        this.state = state;
        this.region = region;
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
