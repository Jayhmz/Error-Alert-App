package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.common.dto.StaffDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;

public interface WriteICOService {
    StaffResponse createStaff(StaffDTO staffDTO);
    StaffResponse editStaff(String staffId, StaffDTO staffDTO);
}
