package com.plantaccion.alartapp.cluster.service;

import com.plantaccion.alartapp.cluster.response.ClusterResponse;

import java.util.List;

public interface ReadClusterService {
    List<ClusterResponse> getAllClusters();
}
