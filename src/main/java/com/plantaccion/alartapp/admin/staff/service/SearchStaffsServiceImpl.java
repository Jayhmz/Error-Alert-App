package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.dto.SearchStaffDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.model.app.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
import com.plantaccion.alartapp.common.repository.auth.AuthenticationRepository;
import com.plantaccion.alartapp.exception.NoContentException;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SearchStaffsServiceImpl implements SearchStaffsService {

    private final AppUserRepository appUserRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ZCHProfileRepository rchRepository;
    private final ICOProfileRepository icoProfileRepository;

    public SearchStaffsServiceImpl(AppUserRepository appUserRepository, AuthenticationRepository authenticationRepository, ZCHProfileRepository rchRepository, ICOProfileRepository icoProfileRepository) {
        this.appUserRepository = appUserRepository;
        this.authenticationRepository = authenticationRepository;
        this.rchRepository = rchRepository;
        this.icoProfileRepository = icoProfileRepository;
    }

    @Override
    public StaffResponse searchByStaff(SearchStaffDTO searchStaffDTO) {
        var staff = appUserRepository.findByEmailOrStaffId(searchStaffDTO.getAttribute());
        if (staff == null){
            throw new StaffNotFoundException("Incorrect staff email or id");
        }
        Map<String, Object> profileResponse = new HashMap<>();
        switch (staff.getRole()) {
            case ZCH -> {
                var profile = rchRepository.findByStaff(staff);
                if (profile != null) {
                    profileResponse.put("id", profile.getId());
                    profileResponse.put("staffId", profile.getStaff().getStaffId());
                    profileResponse.put("createdBy", profile.getCreatedBy().getStaffId());
                    profileResponse.put("deactivation-status", profile.getStaff().isDisabled());

                    var icos = icoProfileRepository.findAllBySupervisor(profile);
                    List<Map<String, Object>> icoOfficers = new ArrayList<>();
                    for (InternalControlOfficerProfile ico : icos) {
                        Map<String, Object> myICOs = new HashMap<>();
                        myICOs.put("ico_staff_id", ico.getIcoStaff().getStaffId());
                        myICOs.put("ico_staff_email", ico.getIcoStaff().getEmail());
                        myICOs.put("ico_staff_role", ico.getIcoStaff().getRole().name());
                        icoOfficers.add(myICOs);
                    }
                    profileResponse.put("ICOs", icoOfficers);
                } else {
                    throw new NoContentException("User profile not found");
                }
            }
            case ICO -> {
                var icoProfile = icoProfileRepository.findByIcoStaff(staff);
                if (icoProfile != null) {
                    profileResponse.put("staffId", icoProfile.getIcoStaff().getStaffId());
                    profileResponse.put("staffEmail", icoProfile.getIcoStaff().getEmail());
                    profileResponse.put("cluster", icoProfile.getSupervisor().getCluster().getName());
                    profileResponse.put("deactivation-status", icoProfile.getIcoStaff().isDisabled());

                    Map<String, Object> zch = new HashMap<>();
                    zch.put("zch_staff_id", icoProfile.getSupervisor().getStaff().getStaffId());
                    zch.put("zch_staff_email", icoProfile.getSupervisor().getStaff().getEmail());
                    zch.put("zch_staff_role", icoProfile.getSupervisor().getStaff().getRole().toString());
                    profileResponse.put("zchProfile", zch);
                } else {
                    throw new NoContentException("User profile not found");
                }
            }
            default -> {
                throw new NoContentException("User profile not found");
            }
        }
        return new StaffResponse(staff.getStaffId(), staff.getEmail(), staff.getRole().name(), profileResponse);
    }
}
