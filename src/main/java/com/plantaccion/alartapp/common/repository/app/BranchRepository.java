package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findBySolId(String solId);
    @Query("SELECT b FROM Branch b where b.cluster.id = :clusterId")
    List<Branch> findBranchesByCluster(Long clusterId);
}
