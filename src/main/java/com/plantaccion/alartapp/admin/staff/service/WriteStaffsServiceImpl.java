package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.dto.RchDTO;
import com.plantaccion.alartapp.common.enums.LoginProvider;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.app.RegionalControlHeadProfile;
import com.plantaccion.alartapp.common.model.auth.AppUser;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import com.plantaccion.alartapp.common.repository.app.RCHProfileRepository;
import com.plantaccion.alartapp.common.repository.auth.AppUserRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WriteStaffsServiceImpl implements WriteStaffsService {
    private final AppUserRepository userRepository;
    private final RCHProfileRepository rchRepository;
    private final ClusterRepository clusterRepository;
    private final PasswordEncoder encoder;

    @Value("${staff.password}")
    private String password;

    public WriteStaffsServiceImpl(AppUserRepository userRepository, RCHProfileRepository rchRepository, ClusterRepository clusterRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.rchRepository = rchRepository;
        this.clusterRepository = clusterRepository;
        this.encoder = encoder;
    }

    @Override
    public StaffResponse createStaff(RchDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));
        var user = new AppUser(staffDTO.getStaffId(), staffDTO.getFirstname(), staffDTO.getLastname(),
                staffDTO.getEmail(), Roles.RCH,
                encoder.encode(password));
        user.setProvider(LoginProvider.BASIC);
        userRepository.save(user);

        var cluster = clusterRepository.findByName(staffDTO.getCluster().toUpperCase());
        var rchProfile = new RegionalControlHeadProfile();
        rchProfile.setCreatedBy(authenticatedUser);
        rchProfile.setStaff(user);
        rchProfile.setCluster(cluster);
        rchRepository.save(rchProfile);

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", rchProfile.getId());
        profileResponse.put("staffId", rchProfile.getStaff().getStaffId());
        profileResponse.put("Cluster", rchProfile.getCluster().getName());
        profileResponse.put("createdBy", rchProfile.getCreatedBy().getStaffId());
        return new StaffResponse(user.getStaffId(), user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getRole().name(), profileResponse);
    }

    @Override
    public StaffResponse editStaff(String staffId, RchDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        var user = userRepository.findByStaffId(staffId);
        if (user == null) {
            throw new StaffNotFoundException("Staff does not exist in our record");
        }
        user.setFirstname(staffDTO.getFirstname());
        user.setLastname(staffDTO.getLastname());
        user.setRole(Roles.RCH);
        user.setProvider(LoginProvider.BASIC);
        userRepository.save(user);

        var cluster = clusterRepository.findByName(staffDTO.getCluster().toUpperCase());
        var rchProfile = rchRepository.findByStaff(user);
        rchProfile.setUpdatedBy(authenticatedUser);
        rchProfile.setCluster(cluster);
        rchProfile.setUpdatedOn(LocalDateTime.now());

        Map<String, Object> profileResponse = new HashMap<>();
        profileResponse.put("id", rchProfile.getId());
        profileResponse.put("staffId", rchProfile.getStaff().getStaffId());
        profileResponse.put("Cluster", rchProfile.getCluster().getName());
        profileResponse.put("createdBy", rchProfile.getCreatedBy().getStaffId());
        profileResponse.put("updatedBy", rchProfile.getUpdatedBy().getStaffId());

        return new StaffResponse(user.getStaffId(), user.getFirstname(),
                user.getLastname(), user.getEmail(), user.getRole().name(), profileResponse);
    }
}
