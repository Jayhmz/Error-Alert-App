package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
import com.plantaccion.alartapp.common.repository.RCHProfileRepository;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadStaffServiceImpl implements ReadStaffService {
    private final AppUserRepository appUserRepository;
    private final RCHProfileRepository rchRepository;

    public ReadStaffServiceImpl(AppUserRepository appUserRepository, RCHProfileRepository rchRepository) {
        this.appUserRepository = appUserRepository;
        this.rchRepository = rchRepository;
    }

    @Override
    public List<StaffResponse> getAllStaff() {
        var allStaffs = appUserRepository.findAll();
        List<StaffResponse> response = new ArrayList<>();
        for (AppUser staff : allStaffs) {
            Map<String, Object> profileResponse = new HashMap<>();
            var profile = rchRepository.findByStaff(staff);
            if (profile != null) {
                profileResponse.put("id", profile.getId());
                profileResponse.put("staffId", profile.getStaff().getStaffId());
                profileResponse.put("createdBy", profile.getCreatedBy().getStaffId());
                if (profile.getUpdatedBy() != null) {
                    profileResponse.put("updatedBy", profile.getUpdatedBy().getStaffId());
                } else {
                    profileResponse.put("updatedBy", null);
                }
            }
            response.add(new StaffResponse(staff.getStaffId(), staff.getFirstname(),
                    staff.getLastname(), staff.getEmail(), staff.getRole().name(), profileResponse));
        }
        return response;
    }

    @Override
    public StaffResponse getOneStaff(String id) {
        var staff = appUserRepository.findByStaffId(id);
        if (staff == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        Map<String, Object> profileResponse = new HashMap<>();
        var profile = rchRepository.findByStaff(staff);
        if (profile != null) {
            profileResponse.put("id", profile.getId());
            profileResponse.put("staffId", profile.getStaff().getStaffId());
            profileResponse.put("createdBy", profile.getCreatedBy().getStaffId());
            if (profile.getUpdatedBy() != null) {
                profileResponse.put("updatedBy", profile.getUpdatedBy().getStaffId());
            } else {
                profileResponse.put("updatedBy", null);
            }
        }
        return new StaffResponse(staff.getStaffId(), staff.getFirstname(),
                staff.getLastname(), staff.getEmail(), staff.getRole().name(), profileResponse);
    }
}
