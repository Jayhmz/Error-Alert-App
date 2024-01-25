package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.common.model.Cluster;
import com.plantaccion.alartapp.common.model.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, String> {
    @Query("SELECT a FROM Alert a WHERE a.script.title = :title ORDER BY a.entryDate DESC")
    Page<Alert> findLastRecordByScriptTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT a FROM Alert a WHERE a.cluster = :cluster AND a.isMailSent <> true AND a.script = :script")
    List<Alert> findAlertsByCluster(@Param("cluster") Cluster cluster, @Param("script") Script script);
}
