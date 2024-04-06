package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.Cluster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
    Cluster findByName(String name);
    @Query("select c from Cluster c")
    Page<Cluster> findClusterByName(Pageable pageable);
}
