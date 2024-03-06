package com.plantaccion.alartapp.config;

import com.plantaccion.alartapp.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.plantaccion.alartapp.common.repository.auth.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@Slf4j
public class BeanConfigurations {

    @Value("${frontend-url}")
    private String frontendUrl;

    private final AppUserRepository repository;
    public BeanConfigurations(AppUserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(alertAppUsernamePasswordAuthenticationProvider()));
    }
    @Bean
    public AuthenticationProvider alertAppUsernamePasswordAuthenticationProvider() {
        return new UsernamePasswordAuthenticationProvider(repository,encoder());
    }
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(List.of(frontendUrl, "http://localhost:5173"));
//        config.addAllowedMethod("*");
//        config.addAllowedHeader("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successfulLogin(){
        return event -> {
          log.info("Authenticated user is : {} "+ event.getAuthentication());
        };
    }
}
