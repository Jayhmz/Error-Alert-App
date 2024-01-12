package com.plantaccion.alartapp.admin.staff.service;

public interface DeleteStaffService {
    void softDelete(Long id);
    void activate(Long id);
    void hardDelete(Long id);
}
