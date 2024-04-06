package com.plantaccion.alartapp.admin.branch.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.common.model.app.Cluster;

import java.util.Map;

public class BranchResponse {
    @JsonProperty("branch_id")
    private String branchId;
    @JsonProperty("branch_name")
    private String branchName;
    @JsonProperty("cluster")
    private Map<String, Object> cluster;

    public BranchResponse(String branchId, String branchName, Map<String, Object> cluster) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.cluster = cluster;
    }
}
