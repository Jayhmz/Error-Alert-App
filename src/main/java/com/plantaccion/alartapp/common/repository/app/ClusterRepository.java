package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
    Cluster findByName(String name);
}
