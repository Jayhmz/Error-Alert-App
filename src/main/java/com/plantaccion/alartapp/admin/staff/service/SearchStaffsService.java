package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.dto.SearchStaffDTO;
import com.plantaccion.alartapp.admin.staff.response.StaffResponse;


public interface SearchStaffsService {
    StaffResponse searchByStaff(SearchStaffDTO searchStaffDTO);
}
