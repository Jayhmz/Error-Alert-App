package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.model.app.ZonalControlHeadProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICOProfileRepository extends JpaRepository<InternalControlOfficerProfile, Long> {
    List<InternalControlOfficerProfile> findAllBySupervisor(ZonalControlHeadProfile rch);
    @Query("SELECT ico from InternalControlOfficerProfile ico where ico.icoStaff.staffId = :staffId")
    InternalControlOfficerProfile findByStaffId(Long staffId);
    @Query("SELECT ico FROM InternalControlOfficerProfile ico where ico.supervisor = :zch ")
    List<InternalControlOfficerProfile> findICOsBySupervisor(@Param("zch") ZonalControlHeadProfile zch);
}
