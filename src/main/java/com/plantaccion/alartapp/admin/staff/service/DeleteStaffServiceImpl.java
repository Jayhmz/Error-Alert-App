package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteStaffServiceImpl implements DeleteStaffService {
    private final AppUserRepository appUserRepository;

    public DeleteStaffServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void softDelete(Long id) {
        var staff = appUserRepository.findByStaffId(id);
        if (staff == null){
            throw new StaffNotFoundException("Staff does not exist in our record");
        };
        staff.setDisabled(true);
        appUserRepository.save(staff);
    }

    @Override
    public void activate(Long id) {
        var staff = appUserRepository.findByStaffId(id);
        if (staff == null){
            throw new StaffNotFoundException("Staff does not exist in our record");
        };
        staff.setDisabled(false);
        appUserRepository.save(staff);
    }

    @Override
    public void hardDelete(Long id) {
        var staff = appUserRepository.findByStaffId(id);
        if (staff == null){
            throw new StaffNotFoundException("Staff does not exist in our record");
        };
        appUserRepository.deleteById(staff.getId());
    }
}
