package com.plantaccion.alartapp.admin.cluster.service;

import com.plantaccion.alartapp.admin.cluster.dto.ClusterDTO;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WriteClusterServiceImpl implements WriteClusterService{

    private final ClusterRepository clusterRepository;

    public WriteClusterServiceImpl(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Override
    @Transactional
    public void createCluster(ClusterDTO clusterDTO) {
        var admin = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown admin"));
        Cluster cluster = new Cluster();
        cluster.setName(clusterDTO.getClusterName().toUpperCase());
        cluster.setRegion(clusterDTO.getRegion().toUpperCase());
        cluster.setState(clusterDTO.getState().toUpperCase());
        cluster.setCreatedBy(admin);
        clusterRepository.save(cluster);
    }

    @Override
    @Transactional
    public void updateCluster(String clusterName, ClusterDTO clusterDTO) {
        var admin = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown admin"));
        var cluster = clusterRepository.findByName(clusterName);
        if(cluster != null){
            cluster.setRegion(clusterDTO.getRegion());
            cluster.setName(clusterDTO.getClusterName());
            cluster.setState(clusterDTO.getState());
            cluster.setUpdatedBy(admin);
            clusterRepository.save(cluster);
        }
    }
}
