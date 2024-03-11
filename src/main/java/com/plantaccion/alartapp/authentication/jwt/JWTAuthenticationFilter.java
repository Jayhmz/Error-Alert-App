package com.plantaccion.alartapp.authentication.jwt;

import com.plantaccion.alartapp.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final RequestMatcher authRequestMatcher = new AntPathRequestMatcher("/api/v1/users/**");
    private final RequestMatcher swaggerRequestMatcher = new AntPathRequestMatcher("/swagger-ui/**");
    private final AppUserRepository userRepository;
    private final JWTService jwtService;
    private final UsernamePasswordAuthenticationProvider authenticationProvider;

    public JWTAuthenticationFilter(AppUserRepository userRepository, JWTService jwtService, UsernamePasswordAuthenticationProvider authenticationProvider) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        res.setHeader("Access-Control-Expose-Headers", "Location");
        String requestHeader = request.getHeader("Authorization");

        if (requestHeader != null && !(authRequestMatcher.matches(request) || swaggerRequestMatcher.matches(request))) {
            String token;

            if (requestHeader.startsWith("Bearer ")) {
                token = requestHeader.split(" ")[1].trim();
            } else {
                token = requestHeader.trim();
            }

            var authenticatedToken = jwtService.validateToken(token);
            if (authenticatedToken != null) {
                var authenticationToken = new UsernamePasswordAuthenticationToken(authenticatedToken.getEmail(),
                        null, getRole(authenticatedToken.getRole()));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
              }
        }

        filterChain.doFilter(request, response);
    }


    private List<GrantedAuthority> getRole(Roles role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

}
