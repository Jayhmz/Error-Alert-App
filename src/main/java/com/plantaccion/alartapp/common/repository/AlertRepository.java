package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AlertRepository extends JpaRepository<Alert, String> {
    @Query("SELECT a FROM Alert a WHERE a.script.title = :title ORDER BY a.entryDate DESC")
    Page<Alert> findLastRecordByScriptTitle(@Param("title") String title, Pageable pageable);
}
