package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.common.dto.RchDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import com.plantaccion.alartapp.rch.dto.IcoDTO;

public interface WriteICOService {
    StaffResponse createStaff(IcoDTO staffDTO);
    StaffResponse editStaff(String staffId, IcoDTO staffDTO);
}
