package com.plantaccion.alartapp.common.utils;

import com.plantaccion.alartapp.common.model.auth.AppUser;
import com.plantaccion.alartapp.common.repository.auth.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppUtils {
    private static AppUserRepository userRepository;

    public AppUtils(AppUserRepository userRepository) {
        AppUtils.userRepository = userRepository;
    }

    public static Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static Optional<AppUser> getAuthenticatedUserDetails(){
        var principal = getAuthenticatedUser().getPrincipal();
        return userRepository.findByEmail(principal.toString());
    }
}
