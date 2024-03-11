package com.plantaccion.alartapp.cluster.service;

import com.plantaccion.alartapp.common.model.app.Cluster;

import java.util.List;

public interface ReadClusterService {
    List<Cluster> getAllClusters();
}
