package com.plantaccion.alartapp.authentication.oauth2.config;

import com.plantaccion.alartapp.authentication.oauth2.service.OAuth2UserService;
import com.plantaccion.alartapp.common.enums.LoginProvider;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.auth.AppUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${frontend-url}")
    private String frontendUrl;
    private final OAuth2UserService userService;
    private final PasswordEncoder encoder;

    public OAuth2SuccessHandler(OAuth2UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        userService.findByEmail(principal.getEmail())
                .ifPresentOrElse(user->{
                    var authenticationToken = new UsernamePasswordAuthenticationToken(principal,
                            null, List.of(new SimpleGrantedAuthority(user.getRole().name())));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }, ()->{
                    AppUser user = new AppUser(principal.getAttribute("sub"),
                            principal.getAttribute("given_name"),
                            principal.getAttribute("family_name"),
                            principal.getEmail(),
                            Roles.ADMIN,
                            encoder.encode("Welcome@123"));
                    user.setProvider(LoginProvider.GOOGLE);
                    userService.save(user);
                    var authenticationToken = new UsernamePasswordAuthenticationToken(user,
                            null, List.of(new SimpleGrantedAuthority(user.getRole().name())));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
        super.onAuthenticationSuccess(request, response, authentication);
    }

}






