package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.common.dto.RchDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;

public interface WriteStaffsService {
    StaffResponse createStaff(RchDTO staffDTO);
    StaffResponse editStaff(String staffId, RchDTO staffDTO);
}
