package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.model.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.model.RegionalControlHeadProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICOProfileRepository extends JpaRepository<InternalControlOfficerProfile, Long> {
    List<InternalControlOfficerProfile> findAllByCreatedBy(RegionalControlHeadProfile rch);

    InternalControlOfficerProfile findByIcoStaff(AppUser icoStaff);

    @Query("SELECT ico from InternalControlOfficerProfile ico where ico.icoStaff.staffId = :staffId")
    InternalControlOfficerProfile findByStaffId(String staffId);
}
