package com.plantaccion.alartapp.admin.branch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.validation.OnlyNumbers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BranchDTO {
    @JsonProperty("branch_id")
    @NotNull(message = "Branch ID cannot be null")
    @NotBlank
    @Size(min = 3, max = 3, message = "Incorrect branch id")
    @OnlyNumbers
    private String branchSolId;

    @JsonProperty("branch_name")
    @NotNull(message = "Branch Name cannot be null")
    @NotBlank
    private String branchName;

    @JsonProperty("cluster")
    @NotNull(message = "Branch cluster cannot be null")
    @NotBlank
    private String cluster;

    public String getBranchId() {
        return branchSolId;
    }

    public void setBranchId(String branchId) {
        this.branchSolId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }
}
