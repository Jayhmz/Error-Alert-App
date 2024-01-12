package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String username);
    AppUser findByStaffId(String staffId);
}
