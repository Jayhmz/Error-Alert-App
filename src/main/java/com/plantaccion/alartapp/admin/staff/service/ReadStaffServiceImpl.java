package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
import com.plantaccion.alartapp.common.repository.auth.AuthenticationRepository;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadStaffServiceImpl implements ReadStaffService {
    private final AppUserRepository appUserRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ZCHProfileRepository rchRepository;

    public ReadStaffServiceImpl(AppUserRepository appUserRepository, AuthenticationRepository authenticationRepository, ZCHProfileRepository rchRepository) {
        this.appUserRepository = appUserRepository;
        this.authenticationRepository = authenticationRepository;
        this.rchRepository = rchRepository;
    }

    @Override
    public List<StaffResponse> getAllStaffProfile() {
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
            response.add(new StaffResponse(staff.getStaffId(), staff.getEmail(), staff.getRole().name(), profileResponse));
        }
        return response;
    }

    @Override
    public StaffResponse getOneStaffProfile(Long id) {
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
        return new StaffResponse(staff.getStaffId(), staff.getEmail(), staff.getRole().name(), profileResponse);
    }

    @Override
    public StaffResponse findOneBankStaff(Long staffId) {
        var staff = authenticationRepository.findById(staffId)
                .orElseThrow(() -> new StaffNotFoundException("Incorrect staff id"));
        return new StaffResponse(staff.getStaffId(), staff.getEmail());
    }
}
