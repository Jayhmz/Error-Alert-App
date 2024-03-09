package com.plantaccion.alartapp.common.repository.auth;

import com.plantaccion.alartapp.common.model.auth.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Long> {

    Optional<AuthenticationEntity> findByEmail(String email);
}
