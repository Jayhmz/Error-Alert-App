package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.model.Cluster;
import com.plantaccion.alartapp.common.model.RegionalControlHeadProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RCHProfileRepository extends JpaRepository<RegionalControlHeadProfile, Long> {
    RegionalControlHeadProfile findByStaff(AppUser appUser);
    RegionalControlHeadProfile findByCluster(Cluster cluster);
}
