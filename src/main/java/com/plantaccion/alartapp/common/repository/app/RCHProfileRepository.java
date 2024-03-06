package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.auth.AppUser;
import com.plantaccion.alartapp.common.model.app.Cluster;
import com.plantaccion.alartapp.common.model.app.RegionalControlHeadProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RCHProfileRepository extends JpaRepository<RegionalControlHeadProfile, Long> {
    RegionalControlHeadProfile findByStaff(AppUser appUser);
    RegionalControlHeadProfile findByCluster(Cluster cluster);
}
