package com.plantaccion.alartapp.rch.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;

import java.util.List;

public interface ReadICOService {
    List<StaffResponse> getAllStaff();

    StaffResponse getOneStaff(String staffId);
}
