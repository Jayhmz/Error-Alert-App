package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.model.app.RegionalControlHeadProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICOProfileRepository extends JpaRepository<InternalControlOfficerProfile, Long> {
    List<InternalControlOfficerProfile> findAllByOnboardedBy(RegionalControlHeadProfile rch);
    @Query("SELECT ico from InternalControlOfficerProfile ico where ico.icoStaff.staffId = :staffId")
    InternalControlOfficerProfile findByStaffId(String staffId);
    @Query("SELECT ico FROM InternalControlOfficerProfile ico where ico.onboardedBy = :rch ")
    List<InternalControlOfficerProfile> findICOsByRCH(@Param("rch") RegionalControlHeadProfile rch);
}
