package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.dto.StaffProfileDTO;
import com.plantaccion.alartapp.common.dto.UpdateStaffProfileDTO;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.model.app.InternalControlOfficerProfile;
import com.plantaccion.alartapp.common.model.app.ZonalControlHeadProfile;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.repository.app.ClusterRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.ZCHProfileRepository;
import com.plantaccion.alartapp.common.utils.AppUtils;
import com.plantaccion.alartapp.exception.ClusterNotFoundException;
import com.plantaccion.alartapp.exception.NoContentException;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WriteStaffsServiceImpl implements WriteStaffsService {

    private final AppUserRepository userRepository;
    private final ZCHProfileRepository zchRepository;
    private final ICOProfileRepository icoRepository;
    private final ClusterRepository clusterRepository;

    public WriteStaffsServiceImpl(AppUserRepository userRepository, ZCHProfileRepository zchRepository,
                                  ICOProfileRepository icoRepository,
                                  ClusterRepository clusterRepository) {
        this.userRepository = userRepository;
        this.zchRepository = zchRepository;
        this.icoRepository = icoRepository;
        this.clusterRepository = clusterRepository;
    }

    @Override
    @Transactional
    public StaffResponse createStaff(StaffProfileDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        switch (Roles.valueOf(staffDTO.getRole().toUpperCase())) {
            case ZCH -> {
                AppUser user = new AppUser(staffDTO.getStaffId(), staffDTO.getEmail(), Roles.ZCH);
                userRepository.save(user);

                var zchProfile = createZCHProfile(staffDTO, authenticatedUser, user);
                Map<String, Object> profileResponse = new HashMap<>();
                profileResponse.put("id", zchProfile.getId());
                profileResponse.put("staffId", zchProfile.getStaff().getStaffId());
                profileResponse.put("Cluster", zchProfile.getCluster().getName());
                profileResponse.put("createdBy", zchProfile.getCreatedBy().getStaffId());
                return new StaffResponse(user.getStaffId(), user.getEmail(), user.getRole().name(), profileResponse);
            }
            case ICO -> {
                AppUser user = new AppUser(staffDTO.getStaffId(), staffDTO.getEmail(), Roles.ICO);
                userRepository.save(user);

                var icoProfile = createICOProfile(staffDTO, authenticatedUser, user);
                Map<String, Object> profileResponse = new HashMap<>();
                profileResponse.put("id", icoProfile.getId());
                profileResponse.put("staffId", icoProfile.getIcoStaff().getStaffId());
                profileResponse.put("Cluster", icoProfile.getSupervisor().getCluster().getName());
                profileResponse.put("createdBy", icoProfile.getSupervisor().getStaff().getStaffId());
                return new StaffResponse(user.getStaffId(), user.getEmail(), user.getRole().name(), profileResponse);
            }
            default -> {
                throw new NoContentException("Unknown user role");
            }
        }
    }

    private ZonalControlHeadProfile createZCHProfile(StaffProfileDTO staffDTO, AppUser authenticatedUser, AppUser user) {
        var cluster = clusterRepository.findByName(staffDTO.getCluster().toUpperCase());
        if (cluster == null) {
            throw new ClusterNotFoundException("Cluster/Zone is not available");
        }
        var zchProfile = new ZonalControlHeadProfile();
        zchProfile.setCreatedBy(authenticatedUser);
        zchProfile.setStaff(user);
        zchProfile.setCluster(cluster);
        zchRepository.save(zchProfile);
        return zchProfile;
    }

    private InternalControlOfficerProfile createICOProfile(StaffProfileDTO staffDTO, AppUser authenticatedUser, AppUser user) {
        var rch = zchRepository.findByStaffId(staffDTO.getSupervisor());
        var icoProfile = new InternalControlOfficerProfile();
        icoProfile.setSupervisor(rch);
        icoProfile.setIcoStaff(user);
        icoRepository.save(icoProfile);
        return icoProfile;
    }

    private ZonalControlHeadProfile updateZCHProfile(Long staffId, UpdateStaffProfileDTO staffDTO) {
        var cluster = clusterRepository.findByName(staffDTO.getCluster().toUpperCase());
        var zchProfile = zchRepository.findByStaffId(staffId);
        zchProfile.setCluster(cluster);
        zchRepository.save(zchProfile);
        return zchProfile;
    }

    private InternalControlOfficerProfile updateICOProfile(Long staffId, UpdateStaffProfileDTO staffDTO) {
        var cluster = clusterRepository.findByName(staffDTO.getCluster().toUpperCase());
        var icoProfile = icoRepository.findByStaffId(staffId);
        var zchProfile = zchRepository.findByStaffId(staffDTO.getSupervisor());
        icoProfile.setSupervisor(zchProfile);
        icoRepository.save(icoProfile);
        return icoProfile;
    }

    @Override
    public StaffResponse editStaff(Long staffId, UpdateStaffProfileDTO staffDTO) {
        var authenticatedUser = AppUtils.getAuthenticatedUserDetails()
                .orElseThrow(() -> new StaffNotFoundException("Unknown Staff/User"));

        var user = userRepository.findByStaffId(staffId);
        switch (Roles.valueOf(user.getRole().toString())) {
            case ZCH -> {
                var rchProfile = updateZCHProfile(staffId, staffDTO);
                Map<String, Object> profileResponse = new HashMap<>();
                profileResponse.put("id", rchProfile.getId());
                profileResponse.put("staffId", rchProfile.getStaff().getStaffId());
                profileResponse.put("Cluster", rchProfile.getCluster().getName());
                profileResponse.put("createdBy", rchProfile.getCreatedBy().getStaffId());
                return new StaffResponse(user.getStaffId(), user.getEmail(), user.getRole().name(), profileResponse);
            }
            case ICO -> {
                var icoProfile = updateICOProfile(staffId, staffDTO);
                Map<String, Object> profileResponse = new HashMap<>();
                profileResponse.put("id", icoProfile.getId());
                profileResponse.put("staffId", icoProfile.getIcoStaff().getStaffId());
                profileResponse.put("Cluster", icoProfile.getSupervisor().getCluster().getName());
                profileResponse.put("createdBy", icoProfile.getSupervisor().getStaff().getStaffId());
                return new StaffResponse(user.getStaffId(), user.getEmail(), user.getRole().name(), profileResponse);
            }
            default -> {
                throw new NoContentException("Unknown user role");
            }
        }
    }
}
