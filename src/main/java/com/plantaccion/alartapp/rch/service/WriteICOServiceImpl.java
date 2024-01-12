package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.common.dto.StaffDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.model.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
import com.plantaccion.alartapp.common.repository.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.RCHProfileRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WriteICOServiceImpl implements WriteICOService{
    private final AppUserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ICOProfileRepository icoProfileRepository;
    private final RCHProfileRepository rchProfileRepository;

    public WriteICOServiceImpl(AppUserRepository userRepository, PasswordEncoder encoder,
                               ICOProfileRepository icoProfileRepository, RCHProfileRepository rchProfileRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.icoProfileRepository = icoProfileRepository;
        this.rchProfileRepository = rchProfileRepository;
    }

    @Override
    public StaffResponse createStaff(StaffDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails();

        var user = new AppUser(staffDTO.getStaffId(), staffDTO.getFirstname(), staffDTO.getLastname(),
                staffDTO.getEmail(), Roles.ICO,
                encoder.encode("Welcome@123"));
        userRepository.save(user);

        var rchProfile = rchProfileRepository.findByStaff(authenticatedUser);
        var icoProfile = new InternalControlOfficerProfile();
        icoProfile.setIcoStaff(user);
        icoProfile.setCreatedBy(rchProfile);
        icoProfileRepository.save(icoProfile);

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", icoProfile.getId());
        profileResponse.put("staffId", icoProfile.getIcoStaff().getStaffId());
        profileResponse.put("createdBy", icoProfile.getCreatedBy().getStaff().getStaffId());
        return new StaffResponse(user.getStaffId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole().name(), profileResponse);

    }

    @Override
    public StaffResponse editStaff(String staffId, StaffDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails();
        var rchProfile = rchProfileRepository.findByStaff(authenticatedUser);

        var user = userRepository.findByStaffId(staffId);
        if (user == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        user.setFirstname(staffDTO.getFirstname());
        user.setLastname(staffDTO.getLastname());
        user.setRole(Roles.ICO);
        userRepository.save(user);

        var icoProfile = icoProfileRepository.findByIcoStaff(user);
        icoProfile.setUpdatedOn(LocalDateTime.now());
        icoProfile.setUpdatedBy(rchProfile);
        icoProfileRepository.save(icoProfile);

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", icoProfile.getId());
        profileResponse.put("cluster", icoProfile.getCreatedBy().getCluster());
        profileResponse.put("createdBy", icoProfile.getCreatedBy().getStaff().getStaffId());
        return new StaffResponse(user.getStaffId(), user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getRole().name(), profileResponse);
    }
}
