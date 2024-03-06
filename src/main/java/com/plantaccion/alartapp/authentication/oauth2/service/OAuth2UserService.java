package com.plantaccion.alartapp.authentication.oauth2.service;

import com.plantaccion.alartapp.common.model.auth.AppUser;

import java.util.Optional;


public interface OAuth2UserService {

    Optional<AppUser> findByEmail(String email);
    void save(AppUser user);

}
