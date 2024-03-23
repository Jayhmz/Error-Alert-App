package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query("SELECT a FROM AppUser a WHERE a.email = :email AND a.isDisabled = false")
    Optional<AppUser> findByEmail(@Param("email") String email);
    AppUser findByStaffId(Long staffId);
    @Query("SELECT a FROM AppUser a WHERE a.role = 'ZCH'")
    Page<AppUser> findAllByRoleZCH(Pageable pageable);
    @Query("SELECT a FROM AppUser a WHERE a.role = 'ICO'")
    Page<AppUser> findAllByRoleICO(Pageable pageable);
    @Query("SELECT a FROM AppUser a WHERE a.email = :searchAttribute OR a.staffId = :searchAttribute")
    AppUser findByEmailOrStaffId(String searchAttribute);
}
