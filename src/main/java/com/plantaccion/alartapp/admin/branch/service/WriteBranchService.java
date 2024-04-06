package com.plantaccion.alartapp.admin.branch.service;

import com.plantaccion.alartapp.admin.branch.dto.BranchDTO;
import com.plantaccion.alartapp.admin.branch.response.BranchResponse;

public interface WriteBranchService {
    BranchResponse createBranch(BranchDTO branchDTO);
    BranchResponse updateBranch(String solId, BranchDTO branchDTO);
}
