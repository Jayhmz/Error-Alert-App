package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.enums.LoginProvider;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.model.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
import com.plantaccion.alartapp.common.repository.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.RCHProfileRepository;
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
    private final RCHProfileRepository rchProfileRepository;

    @Value("${staff.password}")
    private String password;
    public WriteICOServiceImpl(AppUserRepository icoRepository, PasswordEncoder encoder,
                               ICOProfileRepository icoProfileRepository, RCHProfileRepository rchProfileRepository) {
        this.icoRepository = icoRepository;
        this.encoder = encoder;
        this.icoProfileRepository = icoProfileRepository;
        this.rchProfileRepository = rchProfileRepository;
    }

    @Override
    public StaffResponse createStaff(IcoDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        var ico = new AppUser(staffDTO.getStaffId(), staffDTO.getFirstname(), staffDTO.getLastname(),
                staffDTO.getEmail(), Roles.ICO, encoder.encode(password));
        ico.setProvider(LoginProvider.BASIC);
        icoRepository.save(ico);

        var rchProfile = rchProfileRepository.findByStaff(authenticatedUser);
        var icoProfile = new InternalControlOfficerProfile();
        icoProfile.setIcoStaff(ico);
        icoProfile.setOnboardedBy(rchProfile);
//        icoProfileRepository.save(icoProfile);

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", icoProfile.getId());
        profileResponse.put("staffId", icoProfile.getIcoStaff().getStaffId());
        profileResponse.put("createdBy", icoProfile.getOnboardedBy().getStaff().getStaffId());
        return new StaffResponse(ico.getStaffId(), ico.getFirstname(), ico.getLastname(),
                ico.getEmail(), ico.getRole().name(), profileResponse);

    }

    @Override
    public StaffResponse editStaff(String staffId, IcoDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var rchProfile = rchProfileRepository.findByStaff(authenticatedUser);

        var user = icoRepository.findByStaffId(staffId);
        if (user == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        user.setFirstname(staffDTO.getFirstname());
        user.setLastname(staffDTO.getLastname());
        user.setRole(Roles.ICO);
        user.setProvider(LoginProvider.BASIC);
        icoRepository.save(user);

        var icoProfile = icoProfileRepository.findByStaffId(user.getStaffId());
        icoProfile.setUpdatedOn(LocalDateTime.now());
        icoProfile.setUpdatedBy(rchProfile);
        icoProfileRepository.save(icoProfile);

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", icoProfile.getId());
        profileResponse.put("cluster", icoProfile.getOnboardedBy().getCluster());
        profileResponse.put("createdBy", icoProfile.getOnboardedBy().getStaff().getStaffId());
        return new StaffResponse(user.getStaffId(), user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getRole().name(), profileResponse);
    }
}
