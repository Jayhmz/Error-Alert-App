package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.Alert;
import com.plantaccion.alartapp.common.model.AppUser;
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
    List<Alert> findAlertsByClusterAndScript(@Param("cluster") Cluster cluster, @Param("script") Script script);
    @Query("SELECT a FROM Alert a WHERE a.cluster = :cluster AND a.resolvedBy IS NULL")
    List<Alert> findAlertsByCluster(@Param("cluster") Cluster cluster);
    @Query("SELECT a FROM Alert a WHERE a.resolvedBy = :staffId ORDER BY a.alertId DESC")
    List<Alert> findAlertsByResolvedBy(@Param("staffId") AppUser staffId);
    @Query("SELECT a FROM Alert a WHERE MONTH(a.generatedOn) = :month")
    List<Alert> findAlertsByMonth(@Param("month") int month);

    //ALERT COUNTS
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.status = 'RESOLVED' AND YEAR(a.generatedOn) = :year")
    Long countOfAlertsReviewedByYear(@Param("year") int year);
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.status = 'RESOLVED' AND MONTH(a.generatedOn) = :month AND YEAR(a.generatedOn) = :year")
    Long countOfAlertsReviewedByMonth(@Param("month") int month, @Param("year") int year);
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.status = 'PENDING' AND MONTH(a.generatedOn) = :month AND YEAR(a.generatedOn) = :year")
    Long countAlertsTakenByMonth(@Param("month") int month, @Param("year") int year);
    @Query("SELECT c.region, COUNT(a) FROM Alert a JOIN a.cluster c GROUP BY c.region")
    List<Object[]> countAlertsByRegion();
    @Query("SELECT c.region, COUNT(a) FROM Alert a JOIN a.cluster c WHERE MONTH(a.generatedOn) = :month AND YEAR(a.generatedOn) = :year GROUP BY c.region")
    List<Object[]> countAlertsByRegionByMonth(@Param("month") int month, @Param("year") int year);
    @Query("SELECT MONTH(a.generatedOn), COUNT(a) FROM Alert a GROUP BY MONTH(a.generatedOn)")
    List<Object[]> countAlertsByMonth();
    @Query("SELECT COUNT(a) FROM Alert a WHERE YEAR(a.generatedOn) = :year")
    Long countAlertsByYear(@Param("year") int year);
    @Query("SELECT COUNT(a) FROM Alert a WHERE MONTH(a.generatedOn) = :month AND YEAR(a.generatedOn) = :year")
    Long countAlertsByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
