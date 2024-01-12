package com.plantaccion.alartapp.config;

import com.plantaccion.alartapp.authentication.jwt.JWTAuthenticationFilter;
import com.plantaccion.alartapp.authentication.jwt.JWTService;
import com.plantaccion.alartapp.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity(securedEnabled = true)
@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final JWTAuthenticationFilter authenticationFilter;

    public AppSecurityConfig(UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider, JWTAuthenticationFilter authenticationFilter) {
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/app/v1/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll();
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/users/**")).permitAll();
                    auth.requestMatchers("/app/v1/admin/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/app/v1/rch/**").hasAuthority("RCH")
                            .anyRequest().authenticated();
                })
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(c->c.disable())
                .headers(h->h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(usernamePasswordAuthenticationProvider)
                .cors(Customizer.withDefaults())
                .build();
    }

}
