package com.plantaccion.alartapp.cluster.service;

import com.plantaccion.alartapp.cluster.response.ClusterResponse;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            response.add(new ClusterResponse(c.getId(), c.getName()));
        }
        return response;
    }

    @Override
    public Page<ClusterResponse> getPaginatedClusters(Pageable pageable) {
        var clusterList = clusterRepository.findClusterByName(pageable);
        return clusterList.map(this::mapToClusterResponse);
    }

    private ClusterResponse mapToClusterResponse(Cluster cluster){
        return new ClusterResponse(cluster.getId(), cluster.getName(), cluster.getState(), cluster.getRegion());
    }
}
