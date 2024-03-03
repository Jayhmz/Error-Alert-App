package com.plantaccion.alartapp.config;

import com.plantaccion.alartapp.authentication.jwt.JWTAuthenticationFilter;
import com.plantaccion.alartapp.authentication.oauth2.config.OAuth2SuccessHandler;
import com.plantaccion.alartapp.authentication.oauth2.config.OAuth2UserAuthorization;
import com.plantaccion.alartapp.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.plantaccion.alartapp.exception.AuthenticationEntryPointException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableMethodSecurity(securedEnabled = true)
@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Value("${frontend-url}")
    private String frontendUrl;

    private final OAuth2SuccessHandler successHandler;
    private final OAuth2UserAuthorization userService;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final JWTAuthenticationFilter authenticationFilter;
    private final AuthenticationEntryPointException authenticationEntryPointException;

    public AppSecurityConfig(OAuth2SuccessHandler successHandler, OAuth2UserAuthorization userService, UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider, JWTAuthenticationFilter authenticationFilter, AuthenticationEntryPointException authenticationEntryPointException) {
        this.successHandler = successHandler;
        this.userService = userService;
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPointException = authenticationEntryPointException;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/swagger-ui/**", "/api/v1/users/**", "/v3/api-docs/**", "/").permitAll()
                            .requestMatchers("/app/v1/admin/**").hasAuthority("ADMIN")
                            .requestMatchers("/app/v1/rch/**").hasAuthority("RCH")
                            .requestMatchers("/app/v1/ico/**").hasAuthority("ICO")
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(usernamePasswordAuthenticationProvider)
//                .oauth2Login(oauth2 -> {
//                    oauth2.userInfoEndpoint(c -> c.oidcUserService(userService));
//                    oauth2.successHandler(successHandler);
//                })
                .logout(l ->
                        l.clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logout"))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl, "http://localhost:5173"));
        configuration.setAllowedMethods(List.of("OPTIONS", "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
