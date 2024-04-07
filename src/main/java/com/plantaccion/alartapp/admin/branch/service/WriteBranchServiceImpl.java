package com.plantaccion.alartapp.admin.branch.service;

import com.plantaccion.alartapp.admin.branch.dto.BranchDTO;
import com.plantaccion.alartapp.admin.branch.response.BranchResponse;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.model.app.Branch;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.repository.app.BranchRepository;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.NoContentException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WriteBranchServiceImpl implements WriteBranchService {

    private final BranchRepository branchRepository;
    private final ClusterRepository clusterRepository;

    public WriteBranchServiceImpl(BranchRepository branchRepository, ClusterRepository clusterRepository) {
        this.branchRepository = branchRepository;
        this.clusterRepository = clusterRepository;
    }

    @Override
    @Transactional
    public BranchResponse createBranch(BranchDTO branchDTO) {
        var admin = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new UsernameNotFoundException("Unknown User"));
        Cluster cluster = getCluster(branchDTO.getCluster());
        Branch branch = new Branch();
        branch.setName(branchDTO.getBranchName().toUpperCase());
        branch.setSolId(branchDTO.getBranchId());
        branch.setCluster(cluster);
        branch.setCreatedBy(admin);
        branchRepository.save(branch);
        Map<String, Object> clusterProperty = new HashMap<>();
        clusterProperty.put("name", branch.getCluster().getName().toUpperCase());
        clusterProperty.put("State", branch.getCluster().getState().toUpperCase());
        clusterProperty.put("region", branch.getCluster().getRegion().toUpperCase());
        return new BranchResponse(branch.getSolId(), branch.getName(), clusterProperty);
    }

    private Cluster getCluster(String clusterName) {
        var cluster = clusterRepository.findByName(clusterName);
        if (cluster == null) {
            throw new NoContentException("Cluster not found");
        }
        return cluster;
    }

    @Override
    @Transactional
    public BranchResponse updateBranch(String solId, BranchDTO branchDTO) {
        var branch = branchRepository.findBySolId(solId)
                .orElseThrow(() -> new NoContentException("Branch not found"));
        branch.setSolId(branchDTO.getBranchId());
        branch.setName(branchDTO.getBranchName().toUpperCase());
        branch.setCluster(getCluster(branchDTO.getCluster()));
        branchRepository.save(branch);
        Map<String, Object> clusterProperty = new HashMap<>();
        clusterProperty.put("name", branch.getCluster().getName().toUpperCase());
        clusterProperty.put("State", branch.getCluster().getState().toUpperCase());
        clusterProperty.put("region", branch.getCluster().getRegion().toUpperCase());
        return new BranchResponse(branch.getSolId(), branch.getName(), clusterProperty);
    }
}
