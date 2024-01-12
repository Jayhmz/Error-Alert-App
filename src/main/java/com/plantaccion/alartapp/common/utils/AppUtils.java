package com.plantaccion.alartapp.common.utils;

import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {
    private static AppUserRepository userRepository;

    public AppUtils(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static AppUser getAuthenticatedUserDetails(){
        var principal = getAuthenticatedUser().getPrincipal();
        return userRepository.findByEmail(principal.toString());
    }
}
