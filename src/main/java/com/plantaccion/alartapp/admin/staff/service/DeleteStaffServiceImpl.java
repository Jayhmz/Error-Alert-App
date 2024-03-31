package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.app.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.model.app.ZonalControlHeadProfile;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteStaffServiceImpl implements DeleteStaffService {
    private final AppUserRepository appUserRepository;
    private final ZCHProfileRepository zchProfileRepository;
    private final ICOProfileRepository icoProfileRepository;

    public DeleteStaffServiceImpl(AppUserRepository appUserRepository, ZCHProfileRepository zchProfileRepository, ICOProfileRepository icoProfileRepository) {
        this.appUserRepository = appUserRepository;
        this.zchProfileRepository = zchProfileRepository;
        this.icoProfileRepository = icoProfileRepository;
    }

    @Override
    public void softDelete(Long id) {
        var staff = appUserRepository.findByStaffId(id);
        if (staff == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        switch(staff.getRole()){
            case ZCH -> {
                var zch = zchProfileRepository.findByStaffId(staff.getStaffId());
                zch.setCluster(null);
                zch.setActive(false);
                zchProfileRepository.save(zch);
                staff.setDisabled(true);
                appUserRepository.save(staff);
            }
            case ICO -> {
                var ico = icoProfileRepository.findByIcoStaff(staff);
                ico.setActive(false);
                icoProfileRepository.save(ico);
                staff.setDisabled(true);
                appUserRepository.save(staff);
            }
            default -> {
                throw new StaffNotFoundException("User role not understood");
            }
        }
    }

    @Override
    public void activate(Long id) {
        var staff = appUserRepository.findByStaffId(id);
        if (staff == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        switch(staff.getRole()){
            case ZCH -> {
                var zch = zchProfileRepository.findByStaffId(staff.getStaffId());
                zch.setActive(true);
                zchProfileRepository.save(zch);
                staff.setDisabled(false);
                appUserRepository.save(staff);
            }
            case ICO -> {
                var ico = icoProfileRepository.findByIcoStaff(staff);
                ico.setActive(true);
                icoProfileRepository.save(ico);
                staff.setDisabled(false);
                appUserRepository.save(staff);
            }
            default -> {
                throw new StaffNotFoundException("User role not understood");
            }
        }
    }

    @Override
    public void hardDelete(Long id) {
        var staff = appUserRepository.findByStaffId(id);
        if (staff == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        switch (staff.getRole()) {
            case ZCH -> {
                var zch = zchProfileRepository.findByStaffId(staff.getStaffId());
                var ICOs = icoProfileRepository.findAllBySupervisor(zch);
                for (InternalControlOfficerProfile ico : ICOs) {
                    ico.setSupervisor(null);
                    icoProfileRepository.save(ico);
                }
                zchProfileRepository.delete(zch);
                appUserRepository.delete(staff);
            }
            case ICO -> {
                var ico = icoProfileRepository.findByIcoStaff(staff);
                icoProfileRepository.delete(ico);
                appUserRepository.delete(staff);
            }
            default -> {
                throw new StaffNotFoundException("User role not understood");
            }
        }
    }
}
