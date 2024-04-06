package com.plantaccion.alartapp.admin.cluster.service;

import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteClusterServiceImpl implements DeleteClusterService{

    private final ClusterRepository clusterRepository;

    public DeleteClusterServiceImpl(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Override
    public boolean deleteCluster(String clusterName) {
        var cluster = clusterRepository.findByName(clusterName);
        if (cluster != null){
            clusterRepository.delete(cluster);
            return true;
        }
        return false;
    }
}
