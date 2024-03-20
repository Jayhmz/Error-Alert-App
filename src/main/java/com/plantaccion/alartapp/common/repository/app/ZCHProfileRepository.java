package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.model.app.ZonalControlHeadProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ZCHProfileRepository extends JpaRepository<ZonalControlHeadProfile, Long> {
    ZonalControlHeadProfile findByStaff(AppUser appUser);
    @Query("SELECT zch FROM ZonalControlHeadProfile zch WHERE zch.staff.staffId = :id")
    ZonalControlHeadProfile findByStaffId(@Param("id") Long id);
    @Query("SELECT zch FROM ZonalControlHeadProfile zch where zch.cluster.name = :cluster")
    ZonalControlHeadProfile findByCluster(String cluster);
}
