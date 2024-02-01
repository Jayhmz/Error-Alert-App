package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String username);
    AppUser findByStaffId(String staffId);
}
