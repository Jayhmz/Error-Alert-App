package com.plantaccion.alartapp.admin.branch.service;

import com.plantaccion.alartapp.admin.branch.response.BranchResponse;
import com.plantaccion.alartapp.common.model.app.Branch;
import com.plantaccion.alartapp.common.repository.app.BranchRepository;
import com.plantaccion.alartapp.exception.NoContentException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReadBranchServiceImpl implements ReadBranchService{
    private final BranchRepository branchRepository;

    public ReadBranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public List<BranchResponse> getBranchesPerCluster(Long clusterId) {
        var branches = branchRepository.findBranchesByCluster(clusterId);
        if (branches == null){
            throw new NoContentException("No branch exist in this cluster");
        }
        List<BranchResponse> responses = new ArrayList<>();
        for (Branch branch : branches){
            Map<String, Object> clusterProperty = new HashMap<>();
            clusterProperty.put("name", branch.getCluster().getName().toUpperCase());
            clusterProperty.put("State", branch.getCluster().getState().toUpperCase());
            clusterProperty.put("region", branch.getCluster().getRegion().toUpperCase());
            responses.add(new BranchResponse(branch.getSolId(), branch.getName(), clusterProperty));
        }
        return responses;
    }

    @Override
    public BranchResponse getBranchBySolId(String solId) {
        var branch = branchRepository.findBySolId(solId)
                .orElseThrow(()->new NoContentException("Branch does not exist"));
        Map<String, Object> clusterProperty = new HashMap<>();
        clusterProperty.put("name", branch.getCluster().getName().toUpperCase());
        clusterProperty.put("State", branch.getCluster().getState().toUpperCase());
        clusterProperty.put("region", branch.getCluster().getRegion().toUpperCase());
        return new BranchResponse(branch.getSolId(), branch.getName(), clusterProperty);

    }
}
