package com.plantaccion.alartapp.admin.staff.service;

import com.plantaccion.alartapp.admin.staff.response.StaffResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadStaffService {
    List<StaffResponse> getAllStaffProfile();
    Page<StaffResponse> getAllZCHs(Pageable pageable);
    Page<StaffResponse> getAllICOs(Pageable pageable);
    StaffResponse getOneStaffProfile(Long staffId);
    StaffResponse findOneBankStaff(Long staffId);

}
