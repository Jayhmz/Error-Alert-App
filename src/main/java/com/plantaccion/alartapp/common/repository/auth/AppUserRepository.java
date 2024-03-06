package com.plantaccion.alartapp.common.repository.auth;

import com.plantaccion.alartapp.common.model.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String username);
    AppUser findByStaffId(String staffId);
}
