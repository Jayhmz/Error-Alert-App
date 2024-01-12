package com.plantaccion.alartapp.cluster.service;

import com.plantaccion.alartapp.common.model.Cluster;
import com.plantaccion.alartapp.common.repository.ClusterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadClusterServiceImpl implements ReadClusterService{
    private final ClusterRepository clusterRepository;

    public ReadClusterServiceImpl(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Override
    public List<Cluster> getAllClusters() {
        return clusterRepository.findAll();
    }
}
