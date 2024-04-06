package com.plantaccion.alartapp.admin.branch.service;

import com.plantaccion.alartapp.admin.branch.response.BranchResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReadBranchService {
    List<BranchResponse> getBranchesPerCluster(Long clusterId);
    BranchResponse getBranchBySolId(String solId);
}
