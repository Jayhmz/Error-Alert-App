package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.dto.SearchStaffDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SearchStaffsServiceImpl implements SearchStaffsService {

    private final AppUserRepository appUserRepository;

    public SearchStaffsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public StaffResponse searchByStaff(SearchStaffDTO searchStaffDTO) {
        var staff = appUserRepository.findByEmailOrStaffId(searchStaffDTO.getAttribute());
        if (staff == null){
            throw new StaffNotFoundException("Incorrect staff email or id");
        }
        return new StaffResponse(staff.getStaffId(), staff.getEmail());
    }
}
