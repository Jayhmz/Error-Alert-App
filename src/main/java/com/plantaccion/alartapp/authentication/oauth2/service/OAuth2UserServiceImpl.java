package com.plantaccion.alartapp.authentication.oauth2.service;

import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2UserServiceImpl implements OAuth2UserService {

    private final AppUserRepository userRepository;

    public OAuth2UserServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(AppUser user) {
        userRepository.save(user);
    }
}
