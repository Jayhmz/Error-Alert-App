package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.model.app.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import com.plantaccion.alartapp.rch.dto.IcoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WriteICOServiceImpl implements WriteICOService{
    private final AppUserRepository icoRepository;
    private final PasswordEncoder encoder;
    private final ICOProfileRepository icoProfileRepository;
    private final ZCHProfileRepository ZCHProfileRepository;

    @Value("${staff.password}")
    private String password;

    public WriteICOServiceImpl(AppUserRepository icoRepository, PasswordEncoder encoder,
                               ICOProfileRepository icoProfileRepository, ZCHProfileRepository ZCHProfileRepository) {
        this.icoRepository = icoRepository;
        this.encoder = encoder;
        this.icoProfileRepository = icoProfileRepository;
        this.ZCHProfileRepository = ZCHProfileRepository;
    }

    @Override
    public StaffResponse createStaff(IcoDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        var ico = new AppUser(staffDTO.getStaffId(), staffDTO.getEmail(), Roles.ICO);
        icoRepository.save(ico);

        var rchProfile = ZCHProfileRepository.findByStaff(authenticatedUser);
        var icoProfile = new InternalControlOfficerProfile();
        icoProfile.setIcoStaff(ico);
        icoProfile.setSupervisor(rchProfile);
//        icoProfileRepository.save(icoProfile);

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", icoProfile.getId());
        profileResponse.put("staffId", icoProfile.getIcoStaff().getStaffId());
        profileResponse.put("createdBy", icoProfile.getSupervisor().getStaff().getStaffId());
        return new StaffResponse(ico.getStaffId(), ico.getEmail(), ico.getRole().name(), profileResponse);

    }

    @Override
    public StaffResponse editStaff(Long staffId, IcoDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var rchProfile = ZCHProfileRepository.findByStaff(authenticatedUser);

        var user = icoRepository.findByStaffId(staffId);
        if (user == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        user.setRole(Roles.ICO);
        icoRepository.save(user);

        var icoProfile = icoProfileRepository.findByStaffId(user.getStaffId());
        icoProfile.setUpdatedOn(LocalDateTime.now());
        icoProfile.setUpdatedBy(rchProfile);
        icoProfileRepository.save(icoProfile);

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", icoProfile.getId());
        profileResponse.put("cluster", icoProfile.getSupervisor().getCluster());
        profileResponse.put("createdBy", icoProfile.getSupervisor().getStaff().getStaffId());
        return new StaffResponse(user.getStaffId(), user.getEmail(), user.getRole().name(), profileResponse);
    }
}
