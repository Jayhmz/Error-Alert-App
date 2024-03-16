package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.common.dto.StaffProfileDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.common.dto.UpdateStaffProfileDTO;

public interface WriteStaffsService {
    StaffResponse createStaff(StaffProfileDTO staffDTO);
    StaffResponse editStaff(Long staffId, UpdateStaffProfileDTO staffDTO);
}
