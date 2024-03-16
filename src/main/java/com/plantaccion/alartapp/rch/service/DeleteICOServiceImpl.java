package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteICOServiceImpl implements DeleteICOService {
  private final ICOProfileRepository icoProfileRepository;

    public DeleteICOServiceImpl(ICOProfileRepository icoProfileRepository) {
        this.icoProfileRepository = icoProfileRepository;
    }

    @Override
    public void softDelete(Long id) {
        var staff = icoProfileRepository.findByStaffId(id);
        if (staff == null){
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        staff.setDisabled(true);
        icoProfileRepository.save(staff);
    }

    @Override
    public void activate(Long id) {
        var staff = icoProfileRepository.findByStaffId(id);
        if (staff == null){
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        staff.setDisabled(false);
        icoProfileRepository.save(staff);
    }

    @Override
    public void hardDelete(Long id) {
        var staff = icoProfileRepository.findByStaffId(id);
        if (staff == null){
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        icoProfileRepository.deleteById(staff.getId());
    }
}
