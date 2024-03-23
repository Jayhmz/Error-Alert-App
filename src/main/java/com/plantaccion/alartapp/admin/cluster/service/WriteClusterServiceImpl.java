package com.plantaccion.alartapp.admin.cluster.service;

import com.plantaccion.alartapp.admin.cluster.dto.ClusterDTO;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WriteClusterServiceImpl implements WriteClusterService{

    private final ClusterRepository clusterRepository;

    public WriteClusterServiceImpl(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Override
    public void createCluster(ClusterDTO clusterDTO) {
        var admin = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown admin"));
        Cluster cluster = new Cluster();
        cluster.setName(clusterDTO.getClusterName());
        cluster.setRegion(clusterDTO.getRegion());
        cluster.setCreatedBy(admin);
        clusterRepository.save(cluster);
    }

    @Override
    public void updateCluster(ClusterDTO clusterDTO) {
        var admin = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown admin"));
        var cluster = clusterRepository.findByName(clusterDTO.getClusterName());
        if(cluster != null){
            cluster.setRegion(clusterDTO.getRegion());
            cluster.setName(clusterDTO.getClusterName());
            cluster.setUpdatedBy(admin);
            clusterRepository.save(cluster);
        }
    }
}
