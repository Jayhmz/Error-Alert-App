package com.plantaccion.alartapp.admin.cluster.service;

import com.plantaccion.alartapp.admin.cluster.dto.ClusterDTO;

public interface WriteClusterService {
    void createCluster(ClusterDTO clusterDTO);
    void updateCluster(ClusterDTO clusterDTO);
}
