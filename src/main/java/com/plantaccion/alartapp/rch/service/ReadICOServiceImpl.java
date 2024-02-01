package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.model.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.model.RegionalControlHeadProfile;
import com.plantaccion.alartapp.common.repository.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.RCHProfileRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadICOServiceImpl implements ReadICOService {

    private final ICOProfileRepository icoProfileRepository;
    private final RCHProfileRepository rchProfileRepository;

    public ReadICOServiceImpl(ICOProfileRepository icoProfileRepository, RCHProfileRepository rchProfileRepository) {
        this.icoProfileRepository = icoProfileRepository;
        this.rchProfileRepository = rchProfileRepository;
    }

    @Override
    public List<StaffResponse> getAllStaff() {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var rchProfile = rchProfileRepository.findByStaff(authenticatedUser);
        var allStaffs = icoProfileRepository.findAllByOnboardedBy(rchProfile);

        List<StaffResponse> response = new ArrayList<>();
        for (InternalControlOfficerProfile profile : allStaffs) {
            Map<String, Object> profileResponse = new HashMap<>();
            var staff = profile.getIcoStaff();
            if (profile != null) {
                profileResponse.put("id", staff.getId());
                profileResponse.put("staffId", staff.getStaffId());
                profileResponse.put("cluster", profile.getOnboardedBy().getCluster());
                profileResponse.put("updatedBy", profile.getUpdatedBy().getStaff().getStaffId());
                response.add(new StaffResponse(staff.getStaffId(), staff.getFirstname(),
                        staff.getLastname(), staff.getEmail(), staff.getRole().name(), profileResponse));
            }
        }
        return response;
    }

    @Override
    public StaffResponse getOneStaff(String staffId) {
        var profile = icoProfileRepository.findByStaffId(staffId);
        if (profile == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        var staff = profile.getIcoStaff();
        Map<String, Object> profileResponse = new HashMap<>();
        if (profile != null) {
            profileResponse.put("id", profile.getId());
            profileResponse.put("staffId", staff.getStaffId());
            profileResponse.put("createdBy", profile.getOnboardedBy().getStaff().getStaffId());
            profileResponse.put("updatedBy", profile.getUpdatedBy().getStaff().getStaffId());
        }
        return new StaffResponse(staff.getStaffId(), staff.getFirstname(),
                staff.getLastname(), staff.getEmail(), staff.getRole().name(), profileResponse);
    }

}
