package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.common.dto.StaffDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;

public interface WriteStaffsService {
    StaffResponse createStaff(StaffDTO staffDTO);
    StaffResponse editStaff(String staffId, StaffDTO staffDTO);
}
