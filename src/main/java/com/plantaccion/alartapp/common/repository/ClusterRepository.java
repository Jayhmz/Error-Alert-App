package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
}
