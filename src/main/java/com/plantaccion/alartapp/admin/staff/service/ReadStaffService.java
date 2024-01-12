package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;

import java.util.List;

public interface ReadStaffService {
    List<StaffResponse> getAllStaff();
    StaffResponse getOneStaff(String staffId);
}
