package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.model.app.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
import com.plantaccion.alartapp.common.repository.auth.AuthenticationRepository;
import com.plantaccion.alartapp.exception.NoContentException;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ICOProfileRepository icoProfileRepository;

    public ReadStaffServiceImpl(AppUserRepository appUserRepository, AuthenticationRepository authenticationRepository, ZCHProfileRepository rchRepository, ICOProfileRepository icoProfileRepository) {
        this.appUserRepository = appUserRepository;
        this.authenticationRepository = authenticationRepository;
        this.rchRepository = rchRepository;
        this.icoProfileRepository = icoProfileRepository;
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
    public Page<StaffResponse> getAllZCHs(Pageable pageable) {
        var zch = appUserRepository.findAllByRoleZCH(pageable);
        if (zch.isEmpty()) {
            throw new NoContentException("No content found");
        }
        return zch.map(this::mapToStaffResponse);
    }

    @Override
    public Page<StaffResponse> getAllICOs(Pageable pageable) {
        var icos = appUserRepository.findAllByRoleICO(pageable);
        if (icos.isEmpty()) {
            throw new NoContentException("No content found");
        }
        return icos.map(this::mapToStaffResponse);
    }

    private StaffResponse mapToStaffResponse(AppUser appuser) {
        Map<String, Object> profileResponse = new HashMap<>();

        var zchProfile = rchRepository.findByStaff(appuser);
        if (zchProfile != null) {
            profileResponse.put("id", zchProfile.getId());
            profileResponse.put("staffId", zchProfile.getStaff().getStaffId());
            profileResponse.put("createdBy", zchProfile.getCreatedBy().getStaffId());
            profileResponse.put("cluster", zchProfile.getCluster().getName());

            var icos = icoProfileRepository.findAllBySupervisor(zchProfile);
            List<Map<String, Object>> icoOfficers = new ArrayList<>();
            for (InternalControlOfficerProfile ico : icos) {
                Map<String, Object> myICOs = new HashMap<>();
                myICOs.put("ico_staff_id", ico.getIcoStaff().getStaffId());
                myICOs.put("ico_staff_email", ico.getIcoStaff().getEmail());
                myICOs.put("ico_staff_role", ico.getIcoStaff().getRole().name());
                icoOfficers.add(myICOs);
            }
            profileResponse.put("ICOs", icoOfficers);
        }

        var icoProfile = icoProfileRepository.findByIcoStaff(appuser);
        if (icoProfile != null) {
            profileResponse.put("id", icoProfile.getId());
            profileResponse.put("staffId", icoProfile.getIcoStaff().getStaffId());
            profileResponse.put("cluster", icoProfile.getSupervisor().getCluster().getName());

            profileResponse.put("zch_staff_id", icoProfile.getSupervisor().getStaff().getStaffId());
            profileResponse.put("zch_staff_email", icoProfile.getSupervisor().getStaff().getEmail());
            profileResponse.put("zch_staff_role", icoProfile.getSupervisor().getStaff().getRole().toString());

        }
        return new StaffResponse(appuser.getStaffId(), appuser.getEmail(),
                appuser.getRole().name(), profileResponse);
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
