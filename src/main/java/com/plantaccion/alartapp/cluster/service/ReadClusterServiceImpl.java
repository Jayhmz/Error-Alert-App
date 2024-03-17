package com.plantaccion.alartapp.cluster.service;

import com.plantaccion.alartapp.cluster.response.ClusterResponse;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadClusterServiceImpl implements ReadClusterService{
    private final ClusterRepository clusterRepository;

    public ReadClusterServiceImpl(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Override
    public List<ClusterResponse> getAllClusters() {
        List<ClusterResponse> response = new ArrayList<>();
        var clusters = clusterRepository.findAll();
        for(Cluster c : clusters){
            ClusterResponse clusterResponse = new ClusterResponse();
            clusterResponse.setCluster(c.getName());
            response.add(clusterResponse);
        }
        return response;
    }
}
