package com.plantaccion.alartapp.cluster.service;

import com.plantaccion.alartapp.cluster.response.ClusterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadClusterService {
    List<ClusterResponse> getAllClusters();
    Page<ClusterResponse> getPaginatedClusters(Pageable pageable);
}
