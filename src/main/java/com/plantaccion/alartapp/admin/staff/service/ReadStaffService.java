package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;

import java.util.List;

public interface ReadStaffService {
    List<StaffResponse> getAllStaffProfile();
    StaffResponse getOneStaffProfile(Long staffId);
    StaffResponse findOneBankStaff(Long staffId);

}
